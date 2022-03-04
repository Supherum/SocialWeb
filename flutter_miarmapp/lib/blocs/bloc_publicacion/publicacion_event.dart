part of 'publicacion_bloc.dart';

abstract class PublicacionEvent extends Equatable {
  const PublicacionEvent();

  @override
  List<Object> get props => [];
}

class GetPublicacioesPublicasEvent extends PublicacionEvent{
  final String token;
  const GetPublicacioesPublicasEvent(this.token);
}
class GetPublicacionesDeUnUsuarioEvent extends PublicacionEvent{
  final String idUsuario;
  final String token;
  const GetPublicacionesDeUnUsuarioEvent(this.idUsuario,this.token);
}

class GetPublicacionesMiasEvent extends PublicacionEvent{
  final String token;
  const GetPublicacionesMiasEvent(this.token);
}
