import 'package:flutter_miarmapp/models/publicacion_response.dart';

abstract class PublicacionRepository {

  Future <List<PublicacionResponse>> getAllPublicacionesPublicas ();
  Future<List<PublicacionResponse>> getAllPublicacionesDeUnUsuario(String id);
  Future <List<PublicacionResponse>> getMisPublicaciones ();
  Future <PublicacionResponse> getUnaPublicacion (String idPublicacion);



}