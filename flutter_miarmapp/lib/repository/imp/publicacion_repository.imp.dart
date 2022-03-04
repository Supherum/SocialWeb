import 'dart:convert';
import 'package:flutter_miarmapp/models/publicacion/publicacion_create_dto.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:http_parser/http_parser.dart';

class PublicacionRepositoryImp extends PublicacionRepository {
  @override
  Future<List<PublicacionResponse>> getAllPublicacionesPublicas(String token) async {
    var response = await http.get(Uri.parse('$baseUrl/publicacion/public'),
        headers: {
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
  Future<List<PublicacionResponse>> getAllPublicacionesDeUnUsuario(
      String nickName, String token) async {
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
      throw Exception(
          'Error del repositorio de publicaciones en $baseUrl/publicacion/usuario/$nickName');
    }
  }

  @override
  Future<List<PublicacionResponse>> getMisPublicaciones(String token) async {
    var response = await http.get(Uri.parse('$baseUrl/publicacion/mia'),
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token'
        });
    if (response.statusCode == 200) {
      return List.from(jsonDecode(response.body))
          .map((e) => PublicacionResponse.fromJson(e))
          .toList();
    } else {
      throw Exception(
          'Error del repositorio de publicaciones en $baseUrl/publicacion/mio');
    }
  }

  @override
  Future<PublicacionResponse> getUnaPublicacion(String idPublicacion,String token) async {
    var response = await http
        .get(Uri.parse('$baseUrl/publicacion/$idPublicacion'), headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer $token'
    });
    if (response.statusCode == 200) {
      return PublicacionResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception(
          'Error del repositorio de publicaciones en $baseUrl/publicacion/$idPublicacion');
    }
  }

  @override
  Future<PublicacionResponse> createPublicacion(
      PublicacionCreateDto dto, XFile file, String token) async {
    var request =
        http.MultipartRequest('POST', Uri.parse('$baseUrl/publicacion/'));
    request.headers.addAll({
      'Content-Type': 'multipart/form-data',
      'Authorization': 'Bearer $token'
    });
    request.files.add(
      await http.MultipartFile.fromPath('file', file.path,
          contentType: MediaType('image', 'jpeg')),
    );
    request.files.add(http.MultipartFile.fromString(
        'publicacion', jsonEncode(dto.toJson()),
        contentType: MediaType('application', 'json')));
    var res = await request.send();
    var body = await res.stream.bytesToString();
    if (res.statusCode == 201) {
      return PublicacionResponse.fromJson(jsonDecode(body));
    } else {
      throw Exception('Error en la petición: $baseUrl/publicacion');
    }
  }

}
