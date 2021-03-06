import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/auth/login_dto.dart';
import 'package:flutter_miarmapp/models/auth/login_response.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';

part 'login_event.dart';
part 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final AuthRepository authRepository;

  LoginBloc(this.authRepository) : super(LoginInitialState()) {
    on<TryToLoginEvent>(_tryToLogin);
  }

  void _tryToLogin(TryToLoginEvent event, Emitter<LoginState> emit) async{
    try{
      final loginResponse= await authRepository.doLogin(event.loginDto);
      emit(LoginSuccessState(loginResponse));
    }on Exception catch(e){
      emit(LoginErrorState(e.toString()));
    }
  }
}
