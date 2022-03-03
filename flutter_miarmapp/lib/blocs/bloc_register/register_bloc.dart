import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/models/auth/login_response.dart';
import 'package:flutter_miarmapp/models/auth/register_dto.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:image_picker/image_picker.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final AuthRepository authRepository;

  RegisterBloc(this.authRepository) : super(RegisterInitialState()) {
    on<DoRegisterEvent>(_doRegister);
    on<SelectImageEvent>(_imagePicker);
    on<SelectDateEvent>(_datePicker);
  }

  void _doRegister(DoRegisterEvent event, Emitter<RegisterState> emit) async {
    final registerResponse= await authRepository.doRegister(event.dto,event.resource);
    try{
      emit (RegisterSuccessState(registerResponse));
    } on Exception catch(e){
      emit (RegisterErrorState(e.toString()));
    }
  }

  void _imagePicker(SelectImageEvent event, Emitter<RegisterState> emit) async{
    final ImagePicker _picker = ImagePicker();
    try{
      final XFile? resource = await  _picker.pickImage(source: event.resource);
      if(resource!=null){
        emit (RegisterImageSuccessState(resource));
      }else{
        emit (const RegisterImageErrorState('Image can not be null'));
      }
    } on Exception catch (e){
      emit (RegisterImageErrorState(e.toString()));
    }
  }

  void _datePicker(SelectDateEvent event, Emitter<RegisterState> emit) async {
    try{
      final DateTime? birthday= await showDatePicker(
        context: event.context,
        initialDate: DateTime.now(),
        firstDate: DateTime(1900),
        lastDate: DateTime.now());
      if(birthday!=null){
        emit(RegisterDateSuccessState(birthday));
      } else{
        emit (const RegisterDateErrorState('Birthday can not be null'));
      }
    } on Exception catch (e){
      emit (RegisterDateErrorState(e.toString()));
    }
  }
}
