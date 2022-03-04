part of 'profile_bloc.dart';

abstract class ProfileState extends Equatable {
  const ProfileState();
  
  @override
  List<Object> get props => [];
}

class ProfileInitialState extends ProfileState {}
class ProfileLoadingState extends ProfileState {}

class ProfilePublicacionesSuccessState extends ProfileState{
  final List<PublicacionResponse> listaPublicaciones;
  const ProfilePublicacionesSuccessState(this.listaPublicaciones);

   @override
  List<Object> get props => [listaPublicaciones];
}
class ProfilePublicacionesErrorState extends ProfileState{
  final String message;
  const ProfilePublicacionesErrorState (this.message);

   @override
  List<Object> get props => [message];
}

class ProfileFollowersSuccessState extends ProfileState{
  final List<UserShortInfo> followersList;
  const ProfileFollowersSuccessState (this.followersList);

   @override
  List<Object> get props => [followersList];
}

class ProfileFollowingSuccessState extends ProfileState{
  final List<UserShortInfo> followingList;
  const ProfileFollowingSuccessState(this.followingList);
   @override
  List<Object> get props => [followingList];
}

class ProfileFollowerErrorState extends ProfileState{
  final String message;
  const ProfileFollowerErrorState(this.message);
   @override
  List<Object> get props => [message];
}

class ProfileFollowingErrorState extends ProfileState{
  final String message;
  const ProfileFollowingErrorState(this.message);
   @override
  List<Object> get props => [message];
}