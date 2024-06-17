package uv.fei.langroup.clientegrpc.clientegrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class ArchivosServiceCliente {
    private static final String GRPC_URL = "";
    private ManagedChannel canal;
    //private ArchivosServiceGrpc.ArchivosServiceStub archivosServiceStub;

    public ArchivosServiceCliente(){
        canal = ManagedChannelBuilder.forTarget(GRPC_URL).usePlaintext().build();
        //archivosServiceStub = ArchivosServiceGrpc.newStub(canal);
    }

    /*public void descargarVideo(String video, StreamObserver<DescargarArchivoResponse> responseObserver) {
        DescargarArchivoRequest request = DescargarArchivoRequest.newBuilder()
                .setNombre(video)
                .build();

        archivosServiceStub.descargarVideo(request, responseObserver);
    }

    public void subirVideo(StreamObserver<DescargarArchivoRequest> requestObserver) {
        StreamObserver<DescargarArchivoResponse> responseObserver = archivosServiceStub.subirVideo(requestObserver);
        // Implement logic to send responses to server if needed
    }

    public void descargarConstancia(String constancia, StreamObserver<DescargarArchivoResponse> responseObserver) {
        DescargarArchivoRequest request = DescargarArchivoRequest.newBuilder()
                .setNombre(constancia)
                .build();

        archivosServiceStub.descargarConstancia(request, responseObserver);
    }

    public void subirConstancia(StreamObserver<DescargarArchivoRequest> requestObserver) {
        StreamObserver<DescargarArchivoResponse> responseObserver = archivosServiceStub.subirConstancia(requestObserver);
        // Implement logic to send responses to server if needed
    }*/

    public void shutdown() {
        canal.shutdown();
    }
}
