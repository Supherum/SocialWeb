import 'dart:convert';

import 'package:flutter_miarmapp/models/auth/login_dto.dart';
import 'package:flutter_miarmapp/models/auth/login_response.dart';
import 'package:flutter_miarmapp/models/auth/register_dto.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:http_parser/http_parser.dart';
class AuthRepositoryImp extends AuthRepository {
  @override
  Future<LoginResponse> doLogin(LoginDto loginDto) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
    };
    final response = await http.post(Uri.parse('$baseUrl/login'),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode(loginDto.toJson()));
    if (response.statusCode == 200) {
      return LoginResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Bad credentials');
    }
  }

  @override
  Future<LoginResponse> doRegister(RegisterDto dto,XFile file) async {
    var request = http.MultipartRequest('POST', Uri.parse('$baseUrl/register/usuario'));
    request.headers.addAll({'Content-Type': 'multipart/form-data'});
    request.files.add(await http.MultipartFile.fromPath('file', file.path));
    request.files.add( http.MultipartFile.fromString('usuario', jsonEncode(dto.toJson()), contentType: MediaType('application','json')));
    var res = await request.send();
    var body = await res.stream.bytesToString();
    if(res.statusCode==201){
      return LoginResponse.fromJson(jsonDecode(body));
    } else{
      throw Exception ('Error en la petición: $baseUrl/register/usuario');
    }    
  }

  @override
  Future<LoginResponse> getUsuarioLogged() async {
    var response = await http.get(Uri.parse('$baseUrl/me'),
        headers: {'Authorization': 'Bearer $token'});
    if (response.statusCode == 200) {
      return LoginResponse.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Error en la petición: $baseUrl/me');
    }
  }
}
