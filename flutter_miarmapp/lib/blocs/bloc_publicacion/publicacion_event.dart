part of 'publicacion_bloc.dart';

abstract class PublicacionEvent extends Equatable {
  const PublicacionEvent();

  @override
  List<Object> get props => [];
}

class GetPublicacioesPublicasEvent extends PublicacionEvent{
  const GetPublicacioesPublicasEvent();
}