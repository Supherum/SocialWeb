import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';

part 'publicacion_event.dart';
part 'publicacion_state.dart';

class PublicacionBloc extends Bloc<PublicacionEvent, PublicacionState> {

  final PublicacionRepository publicacionRepository;

  PublicacionBloc(this.publicacionRepository) : super(PublicacionInitial()) {

    on<PublicacionEvent>(_getPublicacionesPublicas);
  }

  void _getPublicacionesPublicas(PublicacionEvent event, Emitter<PublicacionState> emit) async {
    try{
      final listaPublicaciones = await publicacionRepository.getAllPublicacionesPublicas();
      emit(GetPublicacioesPublicasState(listaPublicaciones));
    }on Exception catch (e){
      emit(GetPublicacioesPublicasStateError(e.toString()));
    }
  }
}
