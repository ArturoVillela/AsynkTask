package com.charlito.getJson;


public interface InterfaceCharlitoJsonGetter {
	
	/**
	 * Solamente pasale el url del json, y crea una instancia de la clase JsonGetter, enviarle los
	 * datos y 'this'.  No olvidar colocar permiso internet. 
	 * {@code Ejemplo de uso: new JsonGetter(this, urlJson, tiempoEsperaMaximo);
	 * }
	 * @param urlJson la direccion url donde se encuentra el archivo json k keremos leer
	 * @param tiempoEsperaMaximo ESPECIFICARLO EN MILISEGUNDOS!! ej:2000 = 2 seg.
	 */
	public void starJsonGetter(String urlJson,int tiempoEsperaMaximo);
	
	/**
	 * Metodo se llamara automaticamente cuando se termine de capturar el json
	 * @param json el archivo json leido o null en caso de error
	 * codigoError, 0 no error,1 error de conexion con internet, 2malformed file, 3tiempo 
	 * exedido, 5 tiempo colocado muy pequenyo.. ponle algo decente, como 2000 o 3000
	 */
	public void resultadoJsonGetter(String json,int codigoError);
	
	

}
