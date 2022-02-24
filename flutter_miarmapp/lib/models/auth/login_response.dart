class LoginResponse {
  LoginResponse({
    required this.id,
    required this.nick,
    required this.nombre,
    required this.apellidos,
    required this.rol,
    required this.fotoPerfil,
    required this.tokenJwt,
    this.direccion,
    this.telefono,
    this.ciudad,
    required this.fechaDeNacimiento,
    required this.private,
  });
  late final String id;
  late final String nick;
  late final String nombre;
  late final String apellidos;
  late final String rol;
  late final String fotoPerfil;
  late final String tokenJwt;
  late final String? direccion;
  late final String? telefono;
  late final String? ciudad;
  late final String fechaDeNacimiento;
  late final bool private;

  LoginResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nick = json['nick'];
    nombre = json['nombre'];
    apellidos = json['apellidos'];
    rol = json['rol'];
    fotoPerfil = json['fotoPerfil'];
    tokenJwt = json['tokenJwt'];
    direccion = json['direccion'];
    telefono = json['telefono'];
    ciudad = json['ciudad'];
    fechaDeNacimiento = json['fechaDeNacimiento'];
    private = json['private'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['nick'] = nick;
    _data['nombre'] = nombre;
    _data['apellidos'] = apellidos;
    _data['rol'] = rol;
    _data['fotoPerfil'] = fotoPerfil;
    _data['tokenJwt'] = tokenJwt;
    _data['direccion'] = direccion;
    _data['telefono'] = telefono;
    _data['ciudad'] = ciudad;
    _data['fechaDeNacimiento'] = fechaDeNacimiento;
    _data['private'] = private;
    return _data;
  }
}
