import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';

part 'publicacion_buttons_event.dart';
part 'publicacion_buttons_state.dart';

class PublicacionButtonsBloc extends Bloc<PublicacionButtonsEvent, PublicacionButtonsState> {
  PublicacionButtonsBloc() : super(PublicacionButtonsInitial()) {
    on<PublicacionButtonsEvent>((event, emit) {
      // TODO: implement event handler
    });
  }
}
