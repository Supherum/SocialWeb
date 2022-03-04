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

class SelectDateEvent extends RegisterEvent{
  final BuildContext context;
  const SelectDateEvent(this.context);
  @override
  List<Object> get props => [context];

}

class DoRegisterEvent extends RegisterEvent{
  final XFile resource;
  final RegisterDto dto;
  const DoRegisterEvent(this.resource,this.dto);
  @override
  List<Object> get props => [resource,dto];

}