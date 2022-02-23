class UsuarioInfoResponse {
  UsuarioInfoResponse({
    required this.id,
    required this.nick,
    required this.nombre,
    required this.apellidos,
    required this.email,
    required this.fotoPerfil,
    required this.fechaNaciemiento,
    required this.seguidores,
  });
  late final String id;
  late final String nick;
  late final String nombre;
  late final String apellidos;
  late final String email;
  late final String fotoPerfil;
  late final String fechaNaciemiento;
  late final int seguidores;
  
  UsuarioInfoResponse.fromJson(Map<String, dynamic> json){
    id = json['id'];
    nick = json['nick'];
    nombre = json['nombre'];
    apellidos = json['apellidos'];
    email = json['email'];
    fotoPerfil = json['fotoPerfil'];
    fechaNaciemiento = json['fechaNaciemiento'];
    seguidores = json['seguidores'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['nick'] = nick;
    _data['nombre'] = nombre;
    _data['apellidos'] = apellidos;
    _data['email'] = email;
    _data['fotoPerfil'] = fotoPerfil;
    _data['fechaNaciemiento'] = fechaNaciemiento;
    _data['seguidores'] = seguidores;
    return _data;
  }
}