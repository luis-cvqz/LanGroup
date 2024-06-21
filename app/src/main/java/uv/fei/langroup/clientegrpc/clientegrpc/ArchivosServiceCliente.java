package uv.fei.langroup.clientegrpc.clientegrpc;

import com.google.protobuf.ByteString;
import com.proto.archivos.Archivos;
import com.proto.archivos.Archivos.DescargarArchivoRequest;
import com.proto.archivos.ArchivosServiceGrpc;

import org.checkerframework.checker.units.qual.A;

import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import uv.fei.langroup.modelo.POJO.ArchivoMultimedia;
import uv.fei.langroup.servicio.servicios.APIClient;

public class ArchivosServiceCliente {
    private ArchivosServiceGrpc.ArchivosServiceBlockingStub blockingStub;

    public ArchivosServiceCliente(){
        ManagedChannel canal = APIClient.iniciarGrpc();
        blockingStub = ArchivosServiceGrpc.newBlockingStub(canal);
    }

    public ArchivoMultimedia descargarVideo(String nombre) {
        ArchivoMultimedia archivoMultimedia = new ArchivoMultimedia();

        DescargarArchivoRequest request = DescargarArchivoRequest.newBuilder()
                .setNombre(nombre)
                .build();
        StreamObserver<Archivos.DescargarArchivoResponse> responseObserver = new StreamObserver<Archivos.DescargarArchivoResponse>() {
            @Override
            public void onNext(Archivos.DescargarArchivoResponse response) {
                if (response.hasArchivo()) {
                    archivoMultimedia.setArchivo(response.getArchivo().toByteArray());
                }

                if (response.hasNombre()){
                    archivoMultimedia.setNombre(response.getNombre());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error al descargar el video: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Descarga completada.");
            }
        };
        blockingStub.descargarVideo(request);

        return archivoMultimedia;
    }

    /*public void subirVideo(byte[] archivo, String nombre) {
        StreamObserver<DescargarArchivoResponse> requestObserver = asyncStub.subirVideo(new StreamObserver<DescargarArchivoRequest>() {
            @Override
            public void onNext(DescargarArchivoRequest value) {
                System.out.println("Subida completada para el archivo: " + value.getNombre());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error al subir el video: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Subida completada.");
            }
        });

        DescargarArchivoResponse response = DescargarArchivoResponse.newBuilder()
                .setArchivo(ByteString.copyFrom(archivo))
                .setNombre(nombre)
                .build();
        requestObserver.onNext(response);
        requestObserver.onCompleted();
    }*/

    public ArchivoMultimedia descargarConstancia(String nombre) {
        ArchivoMultimedia archivoMultimedia = new ArchivoMultimedia();
        DescargarArchivoRequest request = DescargarArchivoRequest.newBuilder()
                .setNombre(nombre)
                .build();
        StreamObserver<Archivos.DescargarArchivoResponse> responseObserver = new StreamObserver<Archivos.DescargarArchivoResponse>() {
            @Override
            public void onNext(Archivos.DescargarArchivoResponse response) {
                if (response.hasArchivo()) {
                    archivoMultimedia.setArchivo(response.getArchivo().toByteArray());
                }

                if (response.hasNombre()){
                    archivoMultimedia.setNombre(response.getNombre());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error al descargar la constancia: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Descarga completada.");
            }
        };
        blockingStub.descargarConstancia(request);

        return archivoMultimedia;
    }

    /*public void subirConstancia(byte[] archivo, String nombre) {
        StreamObserver<DescargarArchivoResponse> requestObserver = asyncStub.subirConstancia(new StreamObserver<DescargarArchivoRequest>() {
            @Override
            public void onNext(DescargarArchivoRequest value) {
                System.out.println("Subida completada para el archivo: " + value.getNombre());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error al subir la constancia: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Subida completada.");
            }
        });

        DescargarArchivoResponse response = DescargarArchivoResponse.newBuilder()
                .setArchivo(ByteString.copyFrom(archivo))
                .setNombre(nombre)
                .build();
        requestObserver.onNext(response);
        requestObserver.onCompleted();
    }*/
}
