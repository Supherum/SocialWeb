import 'package:flutter/material.dart';
import 'package:flutter_miarmapp/models/publicacion/publicacion_response.dart';
import 'package:flutter_miarmapp/styles/font_style.dart';
import 'package:flutter_svg/flutter_svg.dart';

Widget publicacionesBuilder(List<PublicacionResponse> listaPublicacion) {
  return Expanded(
    child: ListView.builder(
        itemCount: listaPublicacion.length,
        itemBuilder: (context, index) {
          return _publicacionItemBuilder(listaPublicacion.elementAt(index));
        }),
  );
}

_publicacionItemBuilder(PublicacionResponse publicacion) {
  return Column(
    children: [
      Container(
        padding: EdgeInsets.symmetric(horizontal: 10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Row(
              children: [
                Container(
                  margin: EdgeInsets.all(5),
                  child: ClipRRect(
                    borderRadius: BorderRadius.circular(20),
                    child: Image.network(
                      _getImage(publicacion.fotoPerfil),
                      fit: BoxFit.cover,
                      width: 30,
                      height: 30,
                    ),
                  ),
                ),
                Text(publicacion.nick)
              ],
            ),
            IconButton(
                onPressed: () => {},
                icon: SvgPicture.asset('assets/icons/more.svg'))
          ],
        ),
      ),
      _imageBuilder(_processImagesResized(publicacion.listaRecursos)),
      Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  SizedBox(
                    width: 40,
                    child: IconButton(
                        onPressed: () {},
                        icon: SvgPicture.asset('assets/icons/heart.svg')),
                  ),
                  SizedBox(
                    width: 40,
                    child: IconButton(
                        onPressed: () {},
                        icon: SvgPicture.asset('assets/icons/comment.svg')),
                  ),
                  SizedBox(
                    width: 40,
                    child: IconButton(
                        onPressed: () {},
                        icon: SvgPicture.asset('assets/icons/share.svg')),
                  ),
                ],
              ),
              SizedBox(
                width: 40,
                child: IconButton(
                  onPressed: () {},
                  icon: SvgPicture.asset('assets/icons/bookmark.svg'),
                ),
              )
            ],
          ),
          Padding(
            padding: const EdgeInsets.only(left: 10),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  publicacion.titulo,
                  style: titlePublicacion,
                ),
                Text(
                  publicacion.descipcion,
                  style: descriptionPublicacion,
                )
              ],
            ),
          )
        ],
      )
    ],
  );
}

Widget _imageBuilder(List<String> listaUrls) {
  bool isLiked = false;
  bool isHearAnimating = false;

  return GestureDetector(
    child: Stack(
      alignment: Alignment.center,
      children: [
        SizedBox(
          height: 300,
          child: PageView.builder(
              scrollDirection: Axis.horizontal,
              itemCount: listaUrls.length,
              itemBuilder: (context, index) {
                return _imageBuilderItem(listaUrls.elementAt(index), context);
              }),
        ),
        /* Icon(
          Icons.favorite,
          color: Colors.white,
          size: 100,
        )*/
      ],
    ),
    onDoubleTap: () {},
  );
}

_imageBuilderItem(String imageUrl, context) {
  return Image.network(
    _getImage(imageUrl),
    fit: BoxFit.cover,
    width: MediaQuery.of(context).size.width,
  );
}

List<String> _processImagesResized(List<String> listaoriginal){
  List<String> lista = [];
  lista=listaoriginal.where((element) => element.contains("R_")).toList();
  return lista;
}

String _getImage(String url) {
  return url.replaceFirst("localhost", "10.0.2.2");
}
