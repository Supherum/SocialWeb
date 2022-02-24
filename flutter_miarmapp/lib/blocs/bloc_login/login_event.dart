part of 'login_bloc.dart';

abstract class LoginEvent extends Equatable {
  const LoginEvent();

  @override
  List<Object> get props => [];
}

class TryToLoginEvent extends LoginEvent{
  final LoginDto loginDto;
  const TryToLoginEvent(this.loginDto);
}
