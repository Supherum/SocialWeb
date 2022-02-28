import 'dart:convert';

import 'package:flutter_miarmapp/models/auth/login_dto.dart';
import 'package:flutter_miarmapp/models/auth/login_response.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:flutter_miarmapp/utils/const.dart';
import 'package:http/http.dart' as http;

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
  Future<LoginResponse> doRegister() {
    // TODO: implement doRegister
    throw UnimplementedError();
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
