import 'dart:convert';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;

class PublicacionRepositoryImp extends PublicacionRepository{
  @override
  Future<List<PublicacionResponse>> getAllPublicacionesPublicas() async {
   var response = await http
        .get(Uri.parse('$baseUrl/publicacion/public'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
    } else {
      throw Exception('PublicacionRepositoryException');
    }
  }

  @override
  Future<List<PublicacionResponse>> getAllPublicacionesDeUnUsuario(String nickName) async {
     var response = await http
        .get(Uri.parse('$baseUrl/publicacion/usuario/$nickName'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
    } else {
        throw Exception('Error del repositorio de publicaciones en $baseUrl/publicacion/usuario/$nickName');
    }
  }

  @override
  Future<List<PublicacionResponse>> getMisPublicaciones() async{
    var response = await http
        .get(Uri.parse('$baseUrl/publicacion/mio'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
    } else {
        throw Exception('Error del repositorio de publicaciones en $baseUrl/publicacion/mio');
    }
  }

  @override
  Future<PublicacionResponse> getUnaPublicacion(String idPublicacion)  async{
     var response = await http
        .get(Uri.parse('$baseUrl/publicacion/$idPublicacion'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return PublicacionResponse.fromJson(jsonDecode(response.body));
    } else {
        throw Exception('Error del repositorio de publicaciones en $baseUrl/publicacion/$idPublicacion');
    }
  }

  
}