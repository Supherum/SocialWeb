class UserShortInfo {
  UserShortInfo({
    required this.id,
    required this.nick,
    required this.fotoPerfil,
  });
  
  late final String id;
  late final String nick;
  late final String fotoPerfil;

  UserShortInfo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nick = json['nick'];
    fotoPerfil = json['fotoPerfil'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['nick'] = nick;
    _data['fotoPerfil'] = fotoPerfil;
    return _data;
  }
}
