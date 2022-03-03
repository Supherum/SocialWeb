class RegisterDto{

  RegisterDto({
  required this.nick,
  required this.nombre,
  required this.apellidos,
  this.email,
  required this.password,
  required this.password2,
  this.uri,
  this.isPrivado,
  this.direccion,
  this.telefono,
  this.ciudad,
  required this.fechaDeNacimiento,
  });

  late final String nick;
  late final String nombre;
  late final String apellidos;
  late final String? email;
  late final String password;
  late final String password2;
  late final String? uri;
  late final bool? isPrivado;
  late final String? direccion;
  late final String? telefono;
  late final String? ciudad;
  late final String fechaDeNacimiento;

    RegisterDto.fromJson(Map<String, dynamic> json){
    nick = json['nick'];
    nombre = json['nombre'];
    email = json['email'];
    apellidos = json['apellidos'];
    password = json['password'];
    password2 = json['password2'];
    uri = json['uri'];
    isPrivado = json['isPrivado'];
    direccion = json['direccion'];
    telefono = json['telefono'];
    ciudad = json['ciudad'];
    fechaDeNacimiento = json['fechaDeNacimiento'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['nick'] = nick;
    _data['nombre'] = nombre;
    _data['email'] = email;
    _data['apellidos'] = apellidos;
    _data['password'] = password;
    _data['password2'] = password2;
    _data['password'] = password;
    _data['uri'] = uri;
    _data['isPrivado'] = isPrivado;
    _data['direccion'] = direccion;
    _data['telefono'] = telefono;
    _data['ciudad'] = ciudad;
    _data['fechaDeNacimiento'] = fechaDeNacimiento;
    return _data;
  }
}