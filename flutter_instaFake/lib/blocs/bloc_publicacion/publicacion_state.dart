part of 'publicacion_bloc.dart';

abstract class PublicacionState extends Equatable {
  const PublicacionState();
  
  @override
  List<Object> get props => [];
}

class PublicacionInitial extends PublicacionState {}
class GetPublicacioesStateError extends PublicacionState{
  final String mensaje;
  const GetPublicacioesStateError(this.mensaje);
    List<Object> get props => [mensaje];
}
class GetPublicacioesPublicasState extends PublicacionState{
  final List<PublicacionResponse> publicacionesPublicas;
  const GetPublicacioesPublicasState(this.publicacionesPublicas);

    List<Object> get props => [publicacionesPublicas];
}
class GetPublicacioesDeUnUsuarioState extends PublicacionState{
  final List<PublicacionResponse> publicacionesDeUnUsuario;
  const GetPublicacioesDeUnUsuarioState(this.publicacionesDeUnUsuario);
    List<Object> get props => [publicacionesDeUnUsuario];
}
class GetPublicacioesMiasState extends PublicacionState{
  final List<PublicacionResponse> publicacionesDeUnUsuario;
  const GetPublicacioesMiasState(this.publicacionesDeUnUsuario);
    List<Object> get props => [publicacionesDeUnUsuario];
}


