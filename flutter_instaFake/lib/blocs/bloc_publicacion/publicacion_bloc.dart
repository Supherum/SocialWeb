

import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';

part 'publicacion_event.dart';
part 'publicacion_state.dart';

class PublicacionBloc extends Bloc<PublicacionEvent, PublicacionState> {

  final PublicacionRepository publicacionRepository;

  PublicacionBloc(this.publicacionRepository) : super(PublicacionInitial()) {

    on<GetPublicacioesPublicasEvent>(_getPublicacionesPublicas);
    on<GetPublicacionesDeUnUsuarioEvent>(_getPublicacionesDeUnUsuario);
    on<GetPublicacionesMiasEvent>(_getPublicacionesMias);


  }

  void _getPublicacionesPublicas(GetPublicacioesPublicasEvent event, Emitter<PublicacionState> emit) async {
    try{
      final listaPublicaciones = await publicacionRepository.getAllPublicacionesPublicas(event.token);
      emit(GetPublicacioesPublicasState(listaPublicaciones));
    }on Exception catch (e){
      emit(GetPublicacioesStateError(e.toString()));
    }
  }

  void _getPublicacionesDeUnUsuario(GetPublicacionesDeUnUsuarioEvent event, Emitter<PublicacionState> emit)  async{
     try{
      final listaPublicaciones = await publicacionRepository.getAllPublicacionesDeUnUsuario(event.idUsuario,event.token);
      emit(GetPublicacioesPublicasState(listaPublicaciones));
    }on Exception catch (e){
      emit(GetPublicacioesStateError(e.toString()));
    }
  }

  void _getPublicacionesMias(GetPublicacionesMiasEvent event, Emitter<PublicacionState> emit) async{
     try{
      final listaPublicaciones = await publicacionRepository.getMisPublicaciones(event.token);
      emit(GetPublicacioesPublicasState(listaPublicaciones));
    }on Exception catch (e){
      emit(GetPublicacioesStateError(e.toString()));
    }
  }
}
