part of 'register_bloc.dart';

abstract class RegisterState extends Equatable {
  const RegisterState();
  
  @override
  List<Object> get props => [];
}

class RegisterInitialState  extends RegisterState {}
class RegisterLoadingState  extends RegisterState{}

class RegisterSuccessState  extends RegisterState{
  final LoginResponse loginResponse;
  const RegisterSuccessState (this.loginResponse);
  @override
  List<Object> get props => [loginResponse];
}

class RegisterErrorState  extends RegisterState{
  final String message;
  const RegisterErrorState (this.message);
  @override
  List<Object> get props => [message];
}

class RegisterImageSuccessState extends RegisterState{
  final XFile file;
  const RegisterImageSuccessState (this.file);
  @override
  List<Object> get props => [file];
}

class RegisterImageErrorState  extends RegisterState{
  final String message;
  const RegisterImageErrorState (this.message);
  @override
  List<Object> get props => [message];
}

class RegisterDateSuccessState  extends RegisterState{
  final DateTime birthday;
  const RegisterDateSuccessState (this.birthday);
}

class RegisterDateErrorState  extends RegisterState{
  final String message;
  const RegisterDateErrorState (this.message);
}