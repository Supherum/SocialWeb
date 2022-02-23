class PeticionesResponse {
  PeticionesResponse({
    required this.nick,
    required this.fotoPerfil,
    required this.id,
  });
  late final String nick;
  late final String fotoPerfil;
  late final String id;
  
  PeticionesResponse.fromJson(Map<String, dynamic> json){
    nick = json['nick'];
    fotoPerfil = json['fotoPerfil'];
    id = json['id'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['nick'] = nick;
    _data['fotoPerfil'] = fotoPerfil;
    _data['id'] = id;
    return _data;
  }
}
