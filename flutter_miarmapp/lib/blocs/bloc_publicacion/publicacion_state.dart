part of 'publicacion_bloc.dart';

abstract class PublicacionState extends Equatable {
  const PublicacionState();
  
  @override
  List<Object> get props => [];
}

class PublicacionInitial extends PublicacionState {}

class GetPublicacioesPublicasState extends PublicacionState{
  final List<PublicacionResponse> publicacionesPublicas;
  const GetPublicacioesPublicasState(this.publicacionesPublicas);

    List<Object> get props => [publicacionesPublicas];

}

class GetPublicacioesPublicasStateError extends PublicacionState{
  final String mensaje;
  const GetPublicacioesPublicasStateError(this.mensaje);

    List<Object> get props => [mensaje];
}

