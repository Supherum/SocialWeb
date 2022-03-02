part of 'publicacion_buttons_bloc.dart';

abstract class PublicacionButtonsEvent extends Equatable {
  const PublicacionButtonsEvent();

  @override
  List<Object> get props => [];
}

class PublicacionLikeEvent {
  final PublicacionResponse publicacion;
  const PublicacionLikeEvent(this.publicacion);
}


