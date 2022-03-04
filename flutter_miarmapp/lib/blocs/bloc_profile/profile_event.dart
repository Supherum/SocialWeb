part of 'profile_bloc.dart';

abstract class ProfileEvent extends Equatable {
  const ProfileEvent();

  @override
  List<Object> get props => [];
}

class ProfileGetMyPostEvent extends ProfileEvent{
  final String token;
  const ProfileGetMyPostEvent(this.token);
}

class ProfileGetMyFollowerEvent extends ProfileEvent{
   final String token;
  const ProfileGetMyFollowerEvent(this.token);
}

class ProfileGetMyFollowingEvent extends ProfileEvent{
  final String token;
  const ProfileGetMyFollowingEvent(this.token);
}
