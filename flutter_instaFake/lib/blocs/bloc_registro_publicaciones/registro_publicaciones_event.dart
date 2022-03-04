part of 'registro_publicaciones_bloc.dart';

abstract class RegistroPublicacionesEvent extends Equatable {
  const RegistroPublicacionesEvent();

  @override
  List<Object> get props => [];
}

class SelectImageEvent extends RegistroPublicacionesEvent {
  final ImageSource resource;
  const SelectImageEvent(this.resource);
  @override
  List<Object> get props => [resource];
}

class DoRegisterEvent extends RegistroPublicacionesEvent {
  final XFile resource;
  final PublicacionCreateDto dto;
  final String token;
  const DoRegisterEvent(this.resource, this.dto, this.token);
  @override
  List<Object> get props => [resource, dto];
}
