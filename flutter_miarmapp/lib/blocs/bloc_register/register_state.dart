part of 'register_bloc.dart';

abstract class RegisterState extends Equatable {
  const RegisterState();
  
  @override
  List<Object> get props => [];
}

class RegisterInitial extends RegisterState {}
class RegisterLoading extends RegisterState{}

class RegisterSuccess extends RegisterState{
  final LoginResponse loginResponse;
  const RegisterSuccess(this.loginResponse);
  @override
  List<Object> get props => [loginResponse];
}

class RegisterError extends RegisterState{
  final String message;
  const RegisterError(this.message);
  @override
  List<Object> get props => [message];
}

class RegisterImageSuccess extends RegisterState{
  final XFile file;
  const RegisterImageSuccess(this.file);
  @override
  List<Object> get props => [file];
}

class RegisterImageError extends RegisterState{
  final String message;
  const RegisterImageError(this.message);
  @override
  List<Object> get props => [message];
}

class RegisterDateSuccess extends RegisterState{
  final DateTime birthday;
  const RegisterDateSuccess(this.birthday);
}

class RegisterDateError extends RegisterState{
  final String message;
  const RegisterDateError(this.message);
}