part of 'publicacion_bloc.dart';

abstract class PublicacionEvent extends Equatable {
  const PublicacionEvent();

  @override
  List<Object> get props => [];
}

class GetPublicacioesPublicasEvent extends PublicacionEvent{
  const GetPublicacioesPublicasEvent();
}
class GetPublicacionesDeUnUsuarioEvent extends PublicacionEvent{
  final String idUsuario;
  const GetPublicacionesDeUnUsuarioEvent(this.idUsuario);
}

class GetPublicacionesMiasEvent extends PublicacionEvent{
  const GetPublicacionesMiasEvent();
}
