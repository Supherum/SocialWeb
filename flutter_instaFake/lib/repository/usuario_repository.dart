
import 'package:flutter_miarmapp/models/usuario/short_user_response.dart';
import 'package:flutter_miarmapp/models/usuario/usuario_info_response.dart';

abstract class UsuarioRespository {

  Future<UsuarioInfoResponse> getUsuarioInfo (String idUsuario,String token);
  Future<List<UserShortInfo>>getFollowers(String token);
  Future<List<UserShortInfo>>getFollowing(String token);
  
}