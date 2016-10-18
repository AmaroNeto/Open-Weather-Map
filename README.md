# Open-Weather-Map

##Intruções de Uso

- Clique no Mapa e selecione o Local que deseja ver as informações do tempo;
- Depois clique no botão no canto direito superior (duas setas em forma de circulo);
- Espere o Download de dados;
- Aparecerá uma lista com as cidades da região selecionada, escolha uma e irá aparece as informações sobre ela;
- As cidades que você visualizou recentemente aparecerão no histórico do App.

##Info sobre o APP

- Disponível para Android a parti da API 17 - Android 4.2/Jelly Bean;
- Suporte a inglês e Português;
- Feito senguindo critérios do Material Design;

##Lógica de Desenvolvimento do APP

O aplicativo foi desenvolvido através de módulos, onde cada modulo fica responsável por um elemento do sistema.

Os módulos feitos são:

- city
- launch
- main
- map
- repository
- util

### City

Módulo responsável por tudo relacionado as locais/cidades.

Classes:

	- City: Classe que define uma cidade;
	- CityAdapter: Classe que cria uma adapter de cidades para um RecicleView.
	- CityHistoricListFragment: Fragmento de Histórico de cidades visualizadas.
	- CityListActivity: Activity de Lista de Cidades;
	- CityListFragment: Define um fragmento que cria a lista de cidades achadas.

### Launch

Módulo responsável pela apresentação do APP.
	
Classes:

	- LaunchActivity: SplashScreen do Aplicativo.

### Main

Módulo que serve para a classe principal do aplicativo (Main).

Classes:

	- Main: Define a Activite principal que reúne o mapa e o histórico.

### Map

Módulo responsável pelo Mapa e suas funcionalidades.

Classes:

	- MapViewFragment: Cria o Mapa e suas funções.
	- BackgroundTask: Classe que faz uma requisição assíncrona ao Open Weather Mapa para buscar informações.

### Repository

Módulo responsável por salvar os dados sobre cidades.

Classes:
	
	- CityController: Salva as informações sobre as cidades.

### Util

Módulo responsável por classes que auxiliam outras no projeto.

Classes:

	- DividerItemDecoration: Desenha uma linha entre itens nas listas.
	- OwmApplication: Prover um contexto em qualquer lugar do projeto.
	- OwmVars: Váriáveis estáticas do projeto.
	- Util: Funções que auxiliam no projeto.

## Observações

 - O repositório não é no disco e sim na memória, então toda vez que o app for morto (pelo user ou sistema) será apagado tudo que estiver no histórico.

 - Percebi que as temperaturas Max, Min e Médias das cidades são iguais, isso é na API do Open Weather Map, busquei tanto como Celsius como Fahrenheit. Exemplo: "main":{"temp":277.373,"temp_min":277.373,"temp_max":277.373,"pressure":778.35,"sea_level":1027.66,"grnd_level":778.35,"humidity":84}.

 - No Exércicio dava está url: http://api.openweathermap.org/data/2.5/find?lat={LAT}&lon={LON}&cnt=15&APPID=<APP_ID>​. Eu só adicionei mais o campo "units=metric" para o resultado já ser em Celsius.

 # Obrigado