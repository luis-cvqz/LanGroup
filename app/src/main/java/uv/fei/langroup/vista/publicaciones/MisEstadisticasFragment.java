package uv.fei.langroup.vista.publicaciones;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uv.fei.langroup.R;
import uv.fei.langroup.modelo.POJO.Publicacion;
import uv.fei.langroup.servicio.DAO.PublicacionDAO;
import uv.fei.langroup.utilidades.SesionSingleton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MisEstadisticasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisEstadisticasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MisEstadisticasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisEstadisticasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisEstadisticasFragment newInstance(String param1, String param2) {
        MisEstadisticasFragment fragment = new MisEstadisticasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_mis_estadisticas, container, false);

        final BarChart barChartPublicaciones = root.findViewById(R.id.chart_publicaciones);

        ArrayList<Publicacion> publicaciones = new ArrayList<>();

        PublicacionDAO.obtenerPublicacionesPorColaborador(SesionSingleton.getInstance().getColaborador().getId(), new Callback<ArrayList<Publicacion>>() {
            @Override
            public void onResponse(Call<ArrayList<Publicacion>> call, Response<ArrayList<Publicacion>> response) {
                if(response.isSuccessful()){
                    publicaciones.addAll(response.body());

                    llenarBarChart(publicaciones, barChartPublicaciones);
                }else{
                    Log.e("Publicacion", "Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Publicacion>> call, Throwable t) {
                Log.e("Publicacion", "Error en la conexión: " + t.getMessage());
            }
        });

        return root;
    }

    private void llenarBarChart(ArrayList<Publicacion> publicaciones, BarChart barChart){
        ArrayList<BarEntry> entries = new ArrayList<>();

        List<Integer> publicacionesPorMes = new ArrayList<>(Collections.nCopies(12, 0));

        Calendar calendar = Calendar.getInstance();

        for(Publicacion publicacion : publicaciones){
            calendar.setTime(publicacion.getFecha());
            int mes = calendar.get(Calendar.MONTH);
            publicacionesPorMes.set(mes, publicacionesPorMes.get(mes) + 1);
        }

        for(int i = 0; i < 12; i++){
            entries.add(new BarEntry(i + 1, publicacionesPorMes.get(i)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Publicaciones por mes");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.BLACK);

        BarData barData = new BarData(dataSet);
        barChart.setData(barData);

        String[] meses = new DateFormatSymbols().getMonths();
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(meses);
        barChart.getXAxis().setValueFormatter(formatter);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);

        barChart.getAxisLeft().setGranularity(1f);

        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);

        barChart.animateY(2000);
        barChart.setFitBars(true);

        barChart.invalidate();
    }
}