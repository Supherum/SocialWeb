part of 'registro_publicaciones_bloc.dart';

abstract class RegistroPublicacionesState extends Equatable {
  const RegistroPublicacionesState();

  @override
  List<Object> get props => [];
}

class RegistroPublicacionesInitialState extends RegistroPublicacionesState {}

class RegisterPublicacionesLoadingState extends RegistroPublicacionesState {}

class RegistroPublicacionSuccessState extends RegistroPublicacionesState {
  final PublicacionResponse publicacionResponse;
  const RegistroPublicacionSuccessState(this.publicacionResponse);
  @override
  List<Object> get props => [publicacionResponse];
}

class RegistroPublicacionErrorState extends RegistroPublicacionesState {
  final String message;
  const RegistroPublicacionErrorState(this.message);
}

class RegisterImageSuccessState extends RegistroPublicacionesState {
  final XFile file;
  const RegisterImageSuccessState(this.file);
  @override
  List<Object> get props => [file];
}

class RegisterImageErrorState extends RegistroPublicacionesState {
  final String message;
  const RegisterImageErrorState(this.message);
  @override
  List<Object> get props => [message];
}
