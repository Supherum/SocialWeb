part of 'register_bloc.dart';

abstract class RegisterEvent extends Equatable {
  const RegisterEvent();

  @override
  List<Object> get props => [];
}
class SelectImageEvent extends RegisterEvent{
  final ImageSource resource;
  const SelectImageEvent(this.resource);
  @override
  List<Object> get props => [resource];
}
