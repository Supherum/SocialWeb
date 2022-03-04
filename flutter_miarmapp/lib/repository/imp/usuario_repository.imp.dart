import 'dart:convert';
import 'package:flutter_miarmapp/models/usuario/short_user_response.dart';
import 'package:flutter_miarmapp/models/usuario/usuario_info_response.dart';
import 'package:flutter_miarmapp/repository/usuario_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;
class UsuarioRepositoryImp implements UsuarioRespository {

  
  Future<List<UserShortInfo>> getSolicitantes(String token) async {
    var response = await http.get(Uri.parse('$baseUrl/usuario/solicitan/me'),
        headers: {'Authorization': 'Bearer $token'});
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => UserShortInfo.fromJson(e))
          .toList();
    } else {
      throw Exception('Error en la petición: $baseUrl/usuario/solicitan/me');
    }
  }

  @override
  Future<List<UserShortInfo>> getFollowers(String token) async {
    var response = await http.get(Uri.parse('$baseUrl/usuario/seguidor/me'),
        headers: {'Authorization': 'Bearer $token'});
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => UserShortInfo.fromJson(e))
          .toList();
    } else {
      throw Exception('Error en la petición: $baseUrl/usuario/seguidor/me');
    }
  }

  @override
  Future<List<UserShortInfo>> getFollowing (String token) async {
    var response = await http.get(Uri.parse('$baseUrl/usuario/sigo/me'),
        headers: {'Authorization': 'Bearer $token'});
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => UserShortInfo.fromJson(e))
          .toList();
    } else {
      throw Exception('Error en la petición: $baseUrl/usuario/sigo/me');
    }
  }

  @override
  Future<UsuarioInfoResponse> getUsuarioInfo(String idUsuario,String token) async {
    var response = await http.get(Uri.parse('$baseUrl/usuario/$idUsuario'),
        headers: {'Authorization': 'Bearer $token'});
    if (response.statusCode == 200) {
      return UsuarioInfoResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Error en la petición: $baseUrl/usuario/sigo/me');
    }
  }

}
