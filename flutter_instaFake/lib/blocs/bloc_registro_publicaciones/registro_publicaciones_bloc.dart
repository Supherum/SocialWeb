import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_create_dto.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:image_picker/image_picker.dart';

part 'registro_publicaciones_event.dart';
part 'registro_publicaciones_state.dart';

class RegistroPublicacionesBloc
    extends Bloc<RegistroPublicacionesEvent, RegistroPublicacionesState> {
  final PublicacionRepository publicacionRepository;

  RegistroPublicacionesBloc(this.publicacionRepository)
      : super(RegistroPublicacionesInitialState()) {
    on<SelectImageEvent>(_selectImagePublicacion);
    on<DoRegisterEvent>(_doRegisterPublicacion);
  }

  void _selectImagePublicacion(
      SelectImageEvent event, Emitter<RegistroPublicacionesState> emit) async {
    final ImagePicker _picker = ImagePicker();
    try {
      final XFile? resource = await _picker.pickImage(source: event.resource);
      if (resource != null) {
        emit(RegisterImageSuccessState(resource));
      } else {
        emit(const RegisterImageErrorState('Image can not be null'));
      }
    } on Exception catch (e) {
      emit(RegisterImageErrorState(e.toString()));
    }
  }

  void _doRegisterPublicacion(
      DoRegisterEvent event, Emitter<RegistroPublicacionesState> emit) async {
    final registerResponse = await publicacionRepository.createPublicacion(
        event.dto, event.resource, event.token);
    try {
      emit(RegistroPublicacionSuccessState(registerResponse));
    } on Exception catch (e) {
      emit(RegistroPublicacionErrorState(e.toString()));
    }
  }
}
