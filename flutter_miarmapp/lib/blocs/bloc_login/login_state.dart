part of 'login_bloc.dart';

abstract class LoginState extends Equatable {
  const LoginState();
  
  @override
  List<Object> get props => [];
}

class LoginInitialState extends LoginState {}
class LoginLoadingState extends LoginState {}

class LoginSuccessState extends LoginState{
  final LoginResponse loginResponse;
  const LoginSuccessState(this.loginResponse); 
  List<Object> get props => [loginResponse];
}

class LoginErrorState extends LoginState{
  final String mensaje;
  const LoginErrorState(this.mensaje);
    List<Object> get props => [mensaje];
}