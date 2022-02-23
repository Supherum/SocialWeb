import 'dart:convert';
import 'package:flutter_miarmapp/models/login_response.dart';
import 'package:flutter_miarmapp/models/peticiones_response.dart';
import 'package:flutter_miarmapp/models/usuario_info_response.dart';
import 'package:flutter_miarmapp/repository/usuario_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;


class UsuarioRepositoryImp implements UsuarioRespository{

    @override
    Future<LoginResponse> getUsuarioLogged () async {
      var response = await http
        .get(Uri.parse('$baseUrl/me'), headers: {
      'Authorization': 'Bearer $token'
      });
      if (response.statusCode==200){
        return LoginResponse.fromJson(jsonDecode(response.body));
      }else{
        throw Exception ('Error en la petición: $baseUrl/me');
      }
    }

    @override
    Future<List<PeticionesResponse>> getSolicitantes () async {
      var response = await http
        .get(Uri.parse('$baseUrl/usuario/solicitan/me'), headers: {
      'Authorization': 'Bearer $token'
      });
      if (response.statusCode==200){
        return List.from(jsonDecode(response.body))
          .map((e) => PeticionesResponse.fromJson(e))
          .toList();
      }else{
        throw Exception ('Error en la petición: $baseUrl/usuario/solicitan/me');
      }
    }

    @override
    Future<List<PeticionesResponse>> getSeguidores () async {
      var response = await http
        .get(Uri.parse('$baseUrl/usuario/seguidor/me'), headers: {
      'Authorization': 'Bearer $token'
      });
      if (response.statusCode==200){
        return List.from(jsonDecode(response.body))
          .map((e) => PeticionesResponse.fromJson(e))
          .toList();
      }else{
        throw Exception ('Error en la petición: $baseUrl/usuario/seguidor/me');
      }
    }

    @override
    Future<List<PeticionesResponse>> getSeguidos () async {
      var response = await http
        .get(Uri.parse('$baseUrl/usuario/sigo/me'), headers: {
      'Authorization': 'Bearer $token'
      });
      if (response.statusCode==200){
        return List.from(jsonDecode(response.body))
          .map((e) => PeticionesResponse.fromJson(e))
          .toList();
      }else{
        throw Exception ('Error en la petición: $baseUrl/usuario/sigo/me');
      }
    }

    @override
    Future<UsuarioInfoResponse> getUsuarioInfo (String idUsuario) async {
        var response = await http
          .get(Uri.parse('$baseUrl/usuario/$idUsuario'), headers: {
        'Authorization': 'Bearer $token'
        });
        if (response.statusCode==200){
          return UsuarioInfoResponse.fromJson(jsonDecode(response.body));
        }else{
          throw Exception ('Error en la petición: $baseUrl/usuario/sigo/me');
        }
      }
    
}