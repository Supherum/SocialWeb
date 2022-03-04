import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_miarmapp/blocs/bloc_profile/profile_bloc.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/models/usuario/short_user_response.dart';
import 'package:flutter_miarmapp/repository/imp/publicacion_repository.imp.dart';
import 'package:flutter_miarmapp/repository/imp/usuario_repository.imp.dart';
import 'package:flutter_miarmapp/repository/publicacion_repository.dart';
import 'package:flutter_miarmapp/repository/usuario_repository.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_miarmapp/utils/preferences.dart';

class ProfilePage extends StatefulWidget {
  const ProfilePage({ Key? key }) : super(key: key);

  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {

  late PublicacionRepository publicacionRepository;
  late UsuarioRespository usuarioRespository;

  num followersNum=0;
  num followingNum=0;
  num postNum=0;

  List<PublicacionResponse>? misPublicaciones;
  List<UserShortInfo>? listFollowers;
  List<UserShortInfo>? listFollowing;

  @override
  void initState() {
    publicacionRepository=PublicacionRepositoryImp();
    usuarioRespository=UsuarioRepositoryImp();
    PreferenceUtils.init();
    super.initState();
  }



  @override
  Widget build(BuildContext context) {
    return 
      BlocProvider(create: (context){
        return ProfileBloc(usuarioRespository, publicacionRepository)
        ..add(ProfileGetMyPostEvent(PreferenceUtils.getString('jwtToken').toString()))
        ..add(ProfileGetMyFollowerEvent(PreferenceUtils.getString('jwtToken').toString()))
        ..add(ProfileGetMyFollowingEvent(PreferenceUtils.getString('jwtToken').toString()));
      },
      child: _logicProfile(),);
  }

  Widget _logicProfile() {
    return Scaffold(
      body: BlocConsumer<ProfileBloc,ProfileState>(
        listenWhen: (context,state){
          return state is ProfilePublicacionesErrorState || state is ProfileFollowerErrorState || state is ProfileFollowingErrorState;
        },
        listener: (context,state){
          if(state is ProfilePublicacionesErrorState){
            return _errorMessage(context,state.message);
          }else if(state is ProfileFollowerErrorState){
            return _errorMessage(context,state.message);
          }else if (state is ProfileFollowingErrorState){
            return _errorMessage(context,state.message);
          }
        },
        buildWhen: (context,state){
          return state is ProfileFollowingSuccessState || state is ProfileFollowersSuccessState || state is ProfilePublicacionesSuccessState || state is ProfileInitialState || state is ProfileLoadingState;
        },
        builder: (context,state){
          if(state is ProfileFollowingSuccessState){
            listFollowing=state.followingList;
            followingNum=state.followingList.length;
            return _body(context);
          } else if (state is ProfileFollowersSuccessState){
            listFollowers=state.followersList;
            followersNum=state.followersList.length;
            return _body(context);
          }else if (state is ProfilePublicacionesSuccessState){
            misPublicaciones=state.listaPublicaciones;
            return _body(context);
          }else{
            return _body(context);
          }
        },
      ) 
    );
  }

   void _errorMessage(BuildContext context, message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }
  Widget _body(context) {
    return  Padding(
      padding: const EdgeInsets.all(15.0),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
               Container(
              width: 100,
              height: 100,
              padding: const EdgeInsets.all(1),
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(100),
                  border: Border.all(
                      color: Colors.orange,
                      width: 2)),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(100),
                child: Image.network(_getImage(
                  PreferenceUtils.getString('fotoPerfil').toString()),
                  fit: BoxFit.fill,
                ),
              ),
            ),

               Column(
                 children: [
                   Text(postNum.toString(),style: TextStyle(fontSize: 20,fontWeight: FontWeight.w500),),
                   const Text('Post',style: TextStyle(color: Colors.grey),)
                 ],
               ),
                Column(
                 children: [
                   Text(followersNum.toString(),style: TextStyle(fontSize: 20,fontWeight: FontWeight.w500),),
                   const Text('Followers',style: TextStyle(color: Colors.grey),)
                 ],
               ),
                Column(
                 children: [
                   Text(followingNum.toString(),style: TextStyle(fontSize: 20,fontWeight: FontWeight.w500),),
                   const Text('Following',style: TextStyle(color: Colors.grey),)
                 ],
               )
            ],
          )
         ,
          Container(
           margin: const EdgeInsets.only(top:30),
           child: Row(
             mainAxisAlignment: MainAxisAlignment.start,
             children: [
               Text(PreferenceUtils.getString('nick').toString(),style: const TextStyle(fontWeight: FontWeight.w700,fontSize: 18),)
             ],
           ),
         ),
         Container(
           margin: const EdgeInsets.only(top: 5),
           child: Row(
             mainAxisAlignment: MainAxisAlignment.start,
             children: [
               Text(PreferenceUtils.getString('nombre').toString()+"   "+PreferenceUtils.getString('apellidos').toString(),style: TextStyle(color:grey,fontSize: 15))
             ],
           ),
         ),
        
          Container(
           margin: const EdgeInsets.only(top:5),
           child: Row(
             mainAxisAlignment: MainAxisAlignment.start,
             children: [
               Text("Fecha de nacimiento:  "+PreferenceUtils.getString('fechaNacimiento').toString(),style:TextStyle(color:grey,fontSize: 15))
             ],
           ),
         ),
         Container(
           margin: const EdgeInsets.only(top: 20),
           child: const Text('Publicaciones',style: TextStyle(color: Colors.blueAccent, fontWeight: FontWeight.w800,fontSize: 22),))
        ,const Divider(),
        _publicacionesBuilder(misPublicaciones)
        ],
      ),
   
  );
  }

  Widget _publicacionesBuilder (List<PublicacionResponse>? list){
    if(list!=null){
      return ListView.builder(
        shrinkWrap: true,
        itemCount: list.length,
        itemBuilder: (context,index){
          return _publicacionItem(list.elementAt(index));
        });
    }else{
      return Center(child: Text('No tines publicaciones aún'),);
    }

  }

  Widget _publicacionItem (PublicacionResponse publicacionResponse){
    return Image.network(_getImage(publicacionResponse.listaRecursos[0]),width: 50,height: 50,);
  }

  String _getImage(String url) {
  return url.replaceFirst("localhost", "10.0.2.2");
}

  

}