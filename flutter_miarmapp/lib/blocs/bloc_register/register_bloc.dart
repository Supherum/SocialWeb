import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/repository/auth_repository.dart';
import 'package:image_picker/image_picker.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final AuthRepository authRepository;

  RegisterBloc(this.authRepository) : super(RegisterInitial()) {
    on<RegisterEvent>(_doRegister);
  }

  void _doRegister(RegisterEvent event, Emitter<RegisterState> emit) {
  }
}
