import 'package:flutter_miarmapp/models/login_response.dart';
import 'package:flutter_miarmapp/models/peticiones_response.dart';
import 'package:flutter_miarmapp/models/usuario_info_response.dart';

abstract class UsuarioRespository {

  Future<LoginResponse> getUsuarioLogged ();
  Future<UsuarioInfoResponse> getUsuarioInfo (String idUsuario);
  Future<List<PeticionesResponse>> getSolicitantes();
  Future<List<PeticionesResponse>> getSeguidores ();
  Future<List<PeticionesResponse>> getSeguidos ();
  
}