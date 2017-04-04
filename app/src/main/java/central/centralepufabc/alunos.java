package central.centralepufabc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class alunos extends AppCompatActivity {
    private ListView lista;
    private ArrayList<Aluno> arrayAreas=null;
    private Aluno_adapter adapter=null;
    SQLiteDatabase bd;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bd = openOrCreateDatabase("CentralEPUFABCDB", Context.MODE_PRIVATE, null);

        lista=(ListView) findViewById(R.id.lista_alunos);
        arrayAreas=new ArrayList<Aluno>();
        carregar_lista();
    }

    private void carregar_lista(){
        //arrayAreas.add(new Aluno(" ","Jonathan Fátimo. Farmácia, Unifesp",R.drawable.padrao));


        arrayAreas.add(new Aluno("Nome do aluno. Engenharia de (?), UFABC","Todos os ex-alunos abaixo estão dispostos a tirar dúvidas sobre vestibular, universidade, curso e afins. Para entrar em contato, basta tocar no nome do ex-aluno desejado.",R.drawable.padrao));
        arrayAreas.add(new Aluno("Olá, gostaria de deixar registado para os alunos novos que a EP foi a melhor época da minha vida, não só por conta dos professores e dos amigos, mas acredito que assim como eu você aluno que está entrando agora não terá aquela rigidez e método de estudo que tinha no ensino médio. Os professores me ensinaram a organizar os meus horários de estudo, me programar para fazer as listas que eles passavam, com isso eu comecei a me interessar pelo o que eu estava estudando, e não apenas a decorar as matérias em si. E sério! você também vai gostar daquela matéria que você achava (chata), e aaah! não tenha vergonha de pedir ajuda para os professores, porque eles jamais negarão ajuda, serão quase a sua segunda família. Outra coisa, como a ep é um lugar muito feliz e animado, que fazemos vários amigos e etc, cuidado para não confundir as coisas, lembrando sempre que você NÃO É ALUNO DA UFABC, você é um aluno de cursinho que quer passar no vestibular, pois eu tive um pouco de dificuldade de separar a zoeira,festas, eventos da faculdade com os meus estudos porque a sala de sinuca com a galera era muito mais atraente do que as matérias de exatas (na qual eu tinha dificuldade). Algumas dicas, que me deram no começo e eu não levei a sério mas LEVE A SÉRIO AGORA POR FAVOR! <3 : Se organize para estudar em casa, não se disperse do seu objetivo, aproveite cada explicação e tire TODAS as dúvidas com os professores isso faz muito diferença nos vestibulares. Caso não obtenha o resultado que esperava após os vestibulares, não fique mal, fique feliz por ter obtido conhecimento e tente sempre novamente. Falo isso porque hoje sou aluna Prouni no curso que eu sempre quis, e com muito mas MUITO incentivo dos professores de GEO e HIST  da EP . Sejam muito Bem-vindos 'novinhxs', e muito obrigada por tudo EP. <3","Aline Murari. Licenciatura Plena em Geografia, Faculdade Sumaré",R.drawable.alinemurari));
        arrayAreas.add(new Aluno("Terminei o ensino médio em 2015, prestei o ENEM e não passei. No ano seguinte, resolvi me inscrever na EPUFABC, mas sem perspectiva alguma para aquele ano. Imaginava que seria horrível e estressante, tal como outros cursinhos. \n" +
                "As aulas começaram e dia após dia eu fui me surpreendendo. Professores ótimos e que mais pareciam amigos dos alunos. Era uma relação muito fraterna, tanto entre aluno e professor, como entre os próprios alunos. Os laços formados foram muito fortes e o apoio mútuo foi algo que me motivou muito a continuar estudando. \n" +
                "Em suma, a EPUFABC foi a melhor experiência da minha vida. Espero que aproveitem muito esse ano, pois ele será inesquecível. ","Bianca Pintol Leite. Medicina Veterinária, Metodista",R.drawable.padrao));

        arrayAreas.add(new Aluno("Olá, me chamo Bruna, tenho 18 anos, e tenho muito orgulho de poder dividir parte da minha história com vocês. Sempre gostei muito de estudar, era daquelas crianças que ficavam tristes se não tivesse lição de casa (risos). Com o tempo vi que  somente com o estudo mudaria minha realidade de vida, então com uns 14 anos comecei a estudar para o Vestibulinho Etec, e passei! Foi muito gratificante, pois estudaria em uma das melhores Escolas do estado. No começo foi Muito difícil, afinal, vim de uma escola com o ensino muito fraco e, no começo, senti muita dificuldade com as matérias do ensino médio, e isso me fez estudar mais. \n" +
                "O Terceirão foi o ano! Era o ano do meu TCC (fazia técnico integrado ao médio) , Enem,  e queria muito fazer o cursinho da Epufabc. Fiz a prova para entrar no cursinho e passeei :) foi uma experiência incrível ! E mesmo que fosse algo que me esgotaria , até por que saia de casa às 6h e voltava às 23h30 não queria levar nada com a \"barriga\" me dediquei e no final acabei fazendo até uma palestra em minha escola sobre Redação ( algo que eu amo) . Foi muito difícil, mas tenho o orgulho de dizer que realizei mais um sonho, e com a ajuda da Epufabc, o melhor cursinho do Brasil ! \n" +
                "Agradeço de coração todo o apoio que recebi dos professores no cursinho, os ensinamentos foram além da sala de aula. \n" +
                "Hoje posso dizer, \"Sou da UFABC !\" Muito ansiosa para o que tem por vir.\n" +
                "Meu próximo sonho agora é poder retribuir o que recebi, um dia poder dar aulas em um cursinho popular.","Bruna Gabriela. BC&H, UFABC",R.drawable.brunagabriela));

        arrayAreas.add(new Aluno("Aí! Mano é muito difícil falar dá EP... O cursinho mais amor do planeta, os professores são super dedicados e além disso ganhar de brinde amigos para o resto dá vida, lembre que será um ano difícil para todos, e sua nota é equivalente ao seu esforço. Então levem a sério os estudos e se divertir é necessário visto que o estresse é supremo... Hahahah no final irá lembra que valeu muito apena! Eu fiz em 2016 na unidade de Mauá, fiz uma única vez e passei na minha segunda opção de curso na UFCG... Cuidado a UFABC é apaixonante e pretendo volta pra lá... Sou muito bom na área de exatas e estou aqui para ajudar os amiguinhos novos, só chamar que estou aqui para o que precisa! E gente vocês iram criar vários crush, amigos e vão pensar que valeu todo sacrifício quando ver sua aprovação seja ela no Sisu, ProUni, Fies... E não é a universidade que te faz é você que faz a universidade... Seja único, faça a diferença e lembre sempre que estudando vamos longe...","Caique Santos. Engenharia de Materiais, UFCG",R.drawable.caiquesantos));
        arrayAreas.add(new Aluno("Sentem o mais proximo possivel da lousa, estejam presentes em todas as aulas, prestem atencão na aula, façam todas as listas, não faltem nos simulados, não desanimem Amém.\n","Filipe Marques. Engenharia cívil, Anhembi morumbi",R.drawable.padrao));
        arrayAreas.add(new Aluno("Opa amiguinhos!!!!! Vim para falar coisas que eu acho que vcs sabem mas precisamos falar pq vcs esquecem..... NÃO VÁ nas FESTAS dos universitários, pois vc vai perder o foco. EU SEI QUE ISSO VAI ACONTECER! (vi pessoas maravilhosas não alcançarem o objetivo por isso, e não ache que com tu vai ser diferente pq não vai). Vá, tente ir nas monitorias, teria me facilitado a vida! ASSISTA TODAS AS AULAS, vc manja da matéria?! Ok mas assite a aula. E por favor faça amigos. Do bem! <3","Francine Oras. Engenharia agronômica, UNESP",R.drawable.francineoras));

        arrayAreas.add(new Aluno("Estudar na federal tornou-se um sonho a partir do momento em que entrei na EPUFABC. Em 2013 ainda estava no Ensino Médio e através de um cartaz na minha escola, E. E. Padre Aristides Greve, conheci o projeto e decidi me inscrever. Para minha surpresa, passei na prova. Larguei os cursos extracurriculares que fazia e comecei a me dedicar para ingresso na UFABC. Fiz o ENEM e por muito pouco não passei na UFABC, porém consegui bolsa integral na PUC para meu curso. Apesar de ser uma grande oportunidade cursar RI numa Universade particular bem conceituada, decidi fazer mais um ano de EP visto que minha maior vontade era entrar na UFABC. \n" +
                "No ano de 2015 consegui realizar esse sonho e novamente conseguir bolsa integral na PUC. É uma sensação inesplicável. Hoje, olhando por tudo o que passei concluo duas coisas, uma é que não me arrependo nenhum minuto de me dedicar aos estudos e ter feito um cursinho para isso, a EPUFABC me ensinou coisas que levo até hoje na minha vida, é muito mais que uma aula para vestibular, e a segunda coisa é que nunca devemos desistir de nossos sonhos independente de qual seja ele, independente de quanto tempo vamos levar pra alcança-lo, é algo único e que somente quem tem um e consegue realizar pode dizer o quão valioso e satisfatório é a sensação de chegar lá.\n" +
                "Me coloco a disposição para eventuais dúvidas, desabafos, reclamações e palavras de incentivo. Podem ter certeza que nenhum esforço será em vão.","Giovanna Nolli. Relações Internacionais, UFABC",R.drawable.giovannanolli));


        arrayAreas.add(new Aluno("A EPUFABC não só me ajudou para o enem como para os outros vestibulares, os professores são ótimos e sempre dão o apoio necessário, e na maioria das vezes passando assuntos não só de enem . Sabendo aproveitar ele será muito útil, mas terá dificuldades, cansaço e será bem puxado, mas todo o esforço será válido, e a resposta no final será gratificante, e abrirá até mesmo novas possibilidades ! ","Jaqueline Pereira. Direito, UNIP",R.drawable.padrao));
        arrayAreas.add(new Aluno("Melhor fase da vida, aproveitem o máximo esse lugar.","Jonathan Fátimo. Farmácia, Unifesp",R.drawable.padrao));

        arrayAreas.add(new Aluno("Entrar na USP sempre foi um sonho, mas impossível ao meu pensar. Comecei a fazer a EP visando particulares conceituadas como a Anhembi Morumbi e Metodista. Fui perseverante até a metade do ano até que o início de uma depreção me fizeram desistir e mesmo após estar me sentindo bem meses depois não me sentia capaz de seguir em frente com os estudos. Fui para o ENEM pronto para ser escurraçado por todas as questões, visto que eu praticamente havia \"jogado a bandeira\" meses antes, mas para minha surpresa nada disso aconteceu. Não fui tão bem como queria ir, mas tudo o que aprendi na EP antes de sair me abriu as portas para um futuro muito bom que é sonhado por muitos independente da instituição escolhida. Hoje estou morando em Pirassununga, estudando na FZEA e agradecido por tudo que aprendi na EP graças a equipe fantástica que compõe  esse maravilhoso projeto.\n" +
                "O que tenho para dizer é que: Nunca desista de seus sonhos e ainda mais, não se limite, ninguém além de você mesmo sabe do que você é capaz. E se por algum momento pensar em desistir, insista e pense que o seu futuro está a um passo de ti!\n" +
                "Boa sorte e bons estudos!","Jow. Medicina veterinária, USP Pirassununga",R.drawable.padrao));
        arrayAreas.add(new Aluno("Oi, alunos novos! Eu não fui a pessoa mais disclipana do curso ( não repitam isto), mas absorvi todo o conhecimento possível, então não sintam vergonha, pergunte aos professores, fora e dentro dá sala, mesmo que seja a dúvida mais básica, pois se não vira uma bola de neve. Também mantenham se aberto a um novo universo, pois entrei muito focada em um curso é faculdade, e me encontro nos dois totalmente diferente, e agora o mais importante, aproveitem isto com muita intensidade, mas, não acabem com suas saúde mental é física, respire, vai dar tudo certo, boa sorte!","Kelly Maiara. Pedagogia, UEMG",R.drawable.kellymayara));

        arrayAreas.add(new Aluno("Eu não sabia muito bem o que dizer aqui e como sou um dos desenvolvedores do app tive a vantagem de ler o que todos os outros ex-alunos mandaram pra depois escrever o meu, obviamente minha ideia era pegar as melhores partes de cada um e fazer um texto 10/10, afinal no ensino médio isso sempre deu certo, porque não daria agora não é mesmo? Mas, como vc deve ter percebido se leu os outros textos, é impossível juntar todos em um só já que são muito diferentes apesar de falarem do mesmo assunto, afinal, nós somos diferentes, então não posso vir aqui falar que esse vai ser o ano mais feliz ou triste da sua vida, que vc vai amar ou odiar a EP, os professores e as matérias, só posso pedir pra que você tente, de verdade, dê o máximo de você pra conquistar o objetivo que fez você entrar aqui, não importa o quão difícil pareça, a maior lição que eu aprendi nesse cursinho (e que ironicamente não caiu em nenhuma das 180 questões do Enem) é que o poder de transformação realmente está na mão da gente. Ah, também gostaria de aproveitar o espaço pra agradecer nominalmente ao Afonso Henrique que me deu aula de História do Brasil (infelizmente ele não dá mais aulas na EP) por suas ótimas histórias/depoimentos motivacionais durante as aulas, hoje eu vejo que realmente a aula dele era a melhor parte da minha semana e me fizeram evoluir muito como pessoa, tá certo?","Kleverson Nascimento. BC&T, UFABC",R.drawable.kleversonex));

        arrayAreas.add(new Aluno("Antes de entrar na faculdade eu achava que o cursinho só me ajudaria a passar no vestibular. Hoje estando no segundo ano, terceiro semestre, vejo o reflexo do que aprendi na EP em praticamente todas as matérias que eu tenho e como isso vem me ajudado a segurar a barra que é a faculdade pública. A gratidão que eu tenho por esse projeto, pela disposição dos alunos da UF, por ter me ajudado a chegar no meu sonho, é imensurável. Acreditem em vocês! (E valorizem as aulas de redação, que basicamente foi o que fez eu conseguir minha vaga)","Lubs Cristina. Arquitetura e Urbanismo, IFSP",R.drawable.lubscristina));
        arrayAreas.add(new Aluno("Tenha foco e determinação no seus estudos, esse ano você irá ver o mundo de uma forma diferente, vai conhecer a determinação, o foco, um amadurecimento, e também aprender a sentar  bunda numa cadeira e estudar. Creio que só de vc estar lendo essa mensagem já monstra o quanto você esta determinado esse ano e buscando a sonhada facul. Apenas acredite no seu potencial e sugue o máximo dos seus professores encha o saco deles, eles iram te passar tudo que você precisa para as provas. Por fim, não posso falar que foi fácil, entretanto, hoje tenho orgulho do meu passado e me ajuda até hoje, toda aprendizagem que tive, serve e muito na facul. Obs: consegui prouni. BOA SORTE E UMA ULTIMA DICA - NÃO FIQUE POSTANDO FOTO DE QUE ESTA ESTUDANDO COM LEGENDAS QUE ESTA SOFRENDO, TODAS PESSOAS QUE FAZEM ISSO NÃO PASSAM, POIS NÃO ESTÃO ESTUDANDO SÓ TIRAM FOTO FINGINDO.","Léo Cardoso. Ciências Contábeis, Esags Fgv",R.drawable.leonardocardoso));
        arrayAreas.add(new Aluno("Se você for um pobre persistente que tem interesse nos cursos da meto se inscreve no fim do ano na bolsa social, se for pré selecionado e sua renda bater a bolsa é tua, e duvidas sobre engenharia civil só mandar email galerinha ❤ ah e a ep é só amooor","Nayra. Engenharia Civil, Metodista",R.drawable.padrao));

        arrayAreas.add(new Aluno("Eu estudei na Escola Preparatória nos anos de 2014 e até a metade de 2015. A EPUFABC foi algo que mudou totalmente minha cabeça em relação a fazer faculdade. \n" +
                "Cresci ouvindo das pessoas ao meu redor que universidades públicas eram melhores que as particulares, mas isso era muito difícil entrar em uma e por isso deveria trabalhar para poder pagar uma particular. \n" +
                "Conheci a EPUFABC através de um amigo que já havia feito e me indicou. Como eu havia terminado o ensino médio e não estava trabalhando para pagar minha faculdade , decidi prestar o vestibular para não ficar parado sem fazer nada da vida. Passei no vestibular e bem na primeira semana de curso pude perceber que a universidade pública não era tão distante assim de mim, apenas questão de esforço. \n" +
                "Com essa ideia em mente, eu passei meu ano de 2014 inteiro só estudando cerca de 10h por dia (a ponto do meu pai chegar em casa e falar pra eu parar de estudar, rs). Prestei o ENEM de 2014 e o infelizmente acabei não passando. Isso foi algo que me fez sentir incapacitado e cheguei a cogitar a ideia de que seria melhor eu realmente pagar uma faculdade. Porém, graças aos meus amigos que me deram apoio eu decidi fazer mais um ano de cursinho. Fiz meu segundo ano da EPUFABC até a metade de 2015 e acabei não terminando, pois prestei vestibular para FATEC e fui aprovado. Neste mesmo ano prestei novamente o ENEM e fui aprovado na UFABC, universidade que eu vi sendo construída desde o zero e sempre tive vontade de fazer parte.","Rafael Correia. BC&T, UFABC",R.drawable.padrao));
        arrayAreas.add(new Aluno("Participei do projeto durante os 8 meses de duração em Mauá-sp (hj a EP foi transferida para sao bernardo) no ano de 2016, podendo alegar q foi o melhor e pior ano da minha vida ao mesmo tempo, vou explicar o porquê:\n" +
                "Cursinho nao é algo fácil, é uma liberdade enorme q vc tem de ficar o dia todo nos rolês ou passar o dia morto na biblioteca, isso te faz amadurecer e criar responsabilidade, para aqueles q vem de escola pública, a EP me proposrcionou uma visão de faculdade q eu nao tinha, o conhecimento dos professores é algo admirável, cada dia é uma experiência nova, apesar da pressão e ansiedade, no cursinho vc irá encontrar amigos pra vida toda, pessoas q vao realmente te incentivar a nao desistir dos seus sonhos, provas reais de q estudar vale a pena, sinto falta de todos eles, a EPUFABC é o melhor lugar do mundo, uma família q te acolhe de uma forma inacreditável. Oito meses me pareceram anos de tristezas e sorrisos, e apesar de estar feliz por estar dentro da universidade, ainda quero voltar e viver aquilo de novo, sinto muita falta das aulas, das monitorias q eu adorava tanto como literatura e história do brasil, a moça da limpeza que cuidava de todos nós (Marli, amamos vc <3), pra vc que esta entrando agr, aproveite cada segundo <3\n" +
                "Sobre a escolha do curso, a ep me ajudou profundamente, já q sou a pessoa mais indecisa do mundo, passei por todos os cursos, lá tive uma grande visao sobre a divisão de áreas como biológicas, exatas e humanas, aos poucos vc vai se achando, vai encontrando seu lugar, e mesmo q eu tenha me \"encontrado\" nos 45 do segundo tempo, sem a EP eu nao estaria aqui.\n" +
                "Por fim, agradeço aos coordenadores maravilhosos, aos professores, especialmente os de redação, a galera q sofreu no vestibular, aos crush, a moça da secretaria q decorou meu numero da chamada, e a vc, vestibulando de 2017, boa sorte na sua caminhada, entre em contato cmg se quiser. Beijos! :)","Renata Vital. Arquitetura e Urbanismo, Uninove",R.drawable.padrao));


        arrayAreas.add(new Aluno("Participar dá EP foi uma das melhores experiências dá minha vida, acrescentou tanto a mim que no posso nem descrever, com professores maravilhosos que me ajudaram, com qualquer tipo de dúvida que chegasse pra tirar com eles, que faziam tudo que estava ao alcance deles pra te ajudar. No final de tudo sai de lá com bolsa integral no curso que eu queria, não podia estar mais satisfeita, só tenho a agradecer a esse projeto MARAVILHOSO!! ❤","Talissa Taglietti. Enfermagem, Universidade São Judas Tadeu",R.drawable.padrao));

        arrayAreas.add(new Aluno("Quando resolvi fazer um cursinho, eu não sabia exatamente o que era um cursinho, não sabia o que era uma universidade pública e até mesmo o que era o vestibular, mas achava que era importante porque todos falavam. Realmente, o vestibular é uma porta de entrada para muitas de oportunidades, eu pude ver isso durante as aulas, mas não é facil alcançar esse objetivo se não tiver dedicação, foco e o principal: Confiança. \n" +
                "Fiz dois anos de EPUFABC e posso dizer que esse curso vai além de estudar para o vestibular. Nesse lugar eu conheci pessoas que torceram por mim e por cada um que estava lá, e acreditaram mais na nossa capacidade do que nós mesmos.\n" +
                "Meu primeiro ano de cursinho foi corrido, tive que aprender o ensino médio inteiro em menos de um ano. Meu segundo ano foi muito mais tenso, já não era tanto pelo conteúdo, e sim pelo medo de reprovar novamente. Desacreditei que iria conseguir, mas graças ao apoio que tive lá dentro, mantive o foco e consegui minha vaga na universidade.\n" +
                "Ter estudado na EPUFABC foi uma das melhores coisas que já me aconteceu, é um trabalho que eu sempre vou admirar. Espero que a EP cresça cada vez mais e realize o sonho de muitos vestibulandos, e que em cada cadeira ocupada exista alguém que dê valor e aproveite ao máximo esse projeto.","Vitória Bárbara. BC&T, UFABC",R.drawable.vitoriabarbara));
        arrayAreas.add(new Aluno("EP é top! Se joga desde o começo e viva todas as experiências q esse projeto tem pra te proporcionar, desde eventos culturais (palestras, exposições) à festinhas da própria UF (q são do caralho).\n" +
                "Não esqueçam de estudar tbm, pq é importante. Não deixem para os últimos meses, nunca dá certo;\n" +
                "Tentem não perder o fretado (principalmente de SBC (linha 5)!!!!!!!!!!!!! Demora muito pra passar);\n" +
                "Comam no RU sim!! Geral fala q é ruim mas dá pra comer;\n" +
                "Tentem não criar uma barreira professor/aluno: vejam eles como amigos q entendem muito sobre tal assunto. Fui perceber isso só no final do ano, o q fez com q essa barreira criada por mim mesmo não deixasse eu interagir mais com os professores;\n" +
                "Enfim, aproveitem!! Pq passa muito rápido.\n" +
                "Bom ano a todos. Bjs.","Wemerson Silva. Psicologia, Mackenzie",R.drawable.wemersonsilva));
        arrayAreas.add(new Aluno("Foram alguns dos melhores momentos da minha vida conhecimento que obtive, amigos que eu fiz e a base, conhecimentos de cursos e forma de ajuda para o estudante que quer estudar fora de sua cidade ou até mesmo estado.","Yuri. BC&T, UFABC",R.drawable.padrao));
        arrayAreas.add(new Aluno("Como acreditamos no seu potencial e temos certeza de que você vai dar o seu máximo para ser aprovado no curso e universidade que deseja no fim desse ano, já deixamos esse espaço reservado para que você possa enviar seu relato no ano que vem e aparecer nessa tela. Boa sorte <3","Você. Curso que você quer, universidade que você quer",R.drawable.padrao));


        adapter=new Aluno_adapter(this,arrayAreas);
        lista.setAdapter(adapter);

    }

    public void voltar(View view) {
        finish();
    }

    public void captura(View view){
        TextView txt=(TextView) view;
        cursor=bd.rawQuery("SELECT nome,email FROM alunos  WHERE nome='"+txt.getText().toString()+"'ORDER BY nome ASC", null);
        cursor.moveToFirst();
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", cursor.getString(1).toString(), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "App Central EPUFABC - Ex alunos");
        startActivity(Intent.createChooser(emailIntent, null));


    }

}
