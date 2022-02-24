import 'package:flutter_miarmapp/models/auth/login_response.dart';
import 'package:flutter_miarmapp/models/usuario/peticiones_response.dart';
import 'package:flutter_miarmapp/models/usuario/usuario_info_response.dart';

abstract class UsuarioRespository {

  Future<UsuarioInfoResponse> getUsuarioInfo (String idUsuario);
  Future<List<PeticionesResponse>> getSolicitantes();
  Future<List<PeticionesResponse>> getSeguidores ();
  Future<List<PeticionesResponse>> getSeguidos ();
  
}