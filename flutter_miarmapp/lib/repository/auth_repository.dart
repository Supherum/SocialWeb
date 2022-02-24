import 'package:flutter_miarmapp/models/auth/login_dto.dart';
import 'package:flutter_miarmapp/models/auth/login_response.dart';

abstract class AuthRepository {

  Future <LoginResponse> doLogin (LoginDto loginDto);
  Future <LoginResponse> doRegister ();
  Future <LoginResponse> getUsuarioLogged ();

}