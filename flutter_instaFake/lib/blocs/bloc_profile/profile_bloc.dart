import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/models/usuario/short_user_response.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/repository/usuario_repository.dart';

part 'profile_event.dart';
part 'profile_state.dart';

class ProfileBloc extends Bloc<ProfileEvent, ProfileState> {
  final UsuarioRespository usuarioRespository;
  final PublicacionRepository publicacionRepository;

  ProfileBloc(this.usuarioRespository,this.publicacionRepository) : super(ProfileInitialState()) {
    on<ProfileGetMyFollowerEvent>(_getFollowers);
    on<ProfileGetMyFollowingEvent>(_getFollowing);
    on<ProfileGetMyPostEvent>(_getMyPost);
  
  }

  void _getFollowers(ProfileGetMyFollowerEvent event, Emitter<ProfileState> emit) async{
      try{
        final List<UserShortInfo>listFollowers=await usuarioRespository.getFollowers(event.token);
        emit (ProfileFollowersSuccessState(listFollowers));
      } on Exception catch (e){
        emit(ProfileFollowerErrorState(e.toString()));
      }
  }

  void _getFollowing(ProfileGetMyFollowingEvent event, Emitter<ProfileState> emit) async{
    try{
      final List<UserShortInfo> listFollowing=await usuarioRespository.getFollowing(event.token);
      emit (ProfileFollowingSuccessState(listFollowing));
    } on Exception catch (e){
      emit (ProfileFollowingErrorState(e.toString()));
    }
  }

  void _getMyPost(ProfileGetMyPostEvent event, Emitter<ProfileState> emit)async{
    try{
      final List<PublicacionResponse> listPost= await publicacionRepository.getMisPublicaciones(event.token);
      emit(ProfilePublicacionesSuccessState(listPost));
    } on Exception catch (e){
      emit (ProfilePublicacionesErrorState(e.toString()));
    }
  }
}
