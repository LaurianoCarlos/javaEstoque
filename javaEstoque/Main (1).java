import java.util.*;

class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    int opcao = -1;
    boolean verificarProduto;
    String codigos_Barra[] = new String[10];
    double valores_Produtos[] = new double[10];
    int quantidade_Prod_Estoque[] = new int[10];

    // metodo armazenar dados aleatorios no estoque
    Random random = new Random();

    for (int i = 0; i < codigos_Barra.length; i++) {
      String c = gerar_Codigo_Barra();
      codigos_Barra[i] = c;
      valores_Produtos[i] = random.nextInt(12);
      quantidade_Prod_Estoque[i] = random.nextInt(100);
    }

    String codigoBarra = "";
    double valorProduto = 0;
    int quant_Prod_Estoque = 0;

    do {

      opcao = Menu();

      switch (opcao) {
      case 1:
        input.nextLine();
        System.out.print("Insira codigo de barra: ");

        codigoBarra = input.nextLine();

        verificarProduto = false;

        //verifica se o produto já está cadastrado, dando valor a variavel verificar_Produto
        for (int i = 0; i < codigos_Barra.length; i++) {
          if (codigos_Barra[i].equalsIgnoreCase(codigoBarra)) {
            verificarProduto = true;
          }
        }

        if (verificarProduto) {
          System.out.println();
          System.out.println("PRODUTO JÁ CADASTRADO NO SISTEMA");
          System.out.println();
          break;
        }

        //verifica se o codigo de barras tem o tamanho correto de 13 caracteres
        int verificarCodigoTamanho = codigoBarra.length();

        if (verificarCodigoTamanho < 13 || verificarCodigoTamanho > 13) {
          System.out.println();
          System.out.println("CÓDIGO INVÁLIDO");
          System.out.println("O CÓDIGO DE CONTER 13 CARACTERES");
          System.out.println();
          break;
        }

        System.out.println();
        System.out.print("Insira valor da unidade: ");
        valorProduto = input.nextDouble();
        System.out.println();

        if (valorProduto < 0) {
          System.out.println();
          System.out.println("VALOR INVÁLIDO");
          System.out.println();
          break;
        }

        System.out.print("Insira a quantidade do produto: ");
        quant_Prod_Estoque = input.nextInt();
        System.out.println();

        if (quant_Prod_Estoque < 0) {
          System.out.println();
          System.out.println("QUANTIDADE INVÁLIDA");
          System.out.println();
          break;
        }
        //amazena as informações preenchidas no array
        codigos_Barra = adicionar_Codigo_Barra(codigos_Barra, codigoBarra);
        valores_Produtos = adicionar_Valor_Produto(valores_Produtos, valorProduto);
        quantidade_Prod_Estoque = adicionar_Prod_Estoque(quantidade_Prod_Estoque, quant_Prod_Estoque);

        System.out.println();
        System.out.println("CADASTRADO COM SUCESSO");
        System.out.println();
        break;

      case 2:

        System.out.println("[1] Alterar valor");
        System.out.println("[2] Alterar quantidade");
        System.out.println("[3] Excluir Produto");
        System.out.println("[4] Excluir ESTOQUE");
        System.out.println();
        System.out.print("Opcao: ");
        opcao = input.nextInt();
        System.out.println();

        //alterar valor de um produto
        if (opcao == 1) {
          valores_Produtos = alterar_Valor(valores_Produtos, codigos_Barra);
          //alterar quantidade de um produto
        } else if (opcao == 2) {
          quantidade_Prod_Estoque = alterar_Quantidade(codigos_Barra, quantidade_Prod_Estoque);
          //metodo para apagar um produto do estoqque
        } else if (opcao == 3) {
          System.out.print("Deseja excluir qual produto?: ");
          input.nextLine();
          codigoBarra = input.nextLine();

          verificarProduto = false;
          //verifica se o produto já está cadastrado,dando valor a variavel verificar_Produto
          for (int i = 0; i < codigos_Barra.length; i++) {
            if (codigos_Barra[i].equalsIgnoreCase(codigoBarra)) {
              verificarProduto = true;
            }
          }

          if (verificarProduto) {
            System.out.println();
            System.out.println("PRODUTO JÁ CADASTRADO NO SISTEMA");
            System.out.println();
            break;

          } else if (verificarProduto == false) {
            System.out.println();
            System.out.println("PRODUTO NÃO ENCONTRADO");
            System.out.println();
            break;
          }
          //remove e exclui as informações do produto selecionado
          valores_Produtos = remover_Valor_Produto(valores_Produtos, codigos_Barra, codigoBarra);
          quantidade_Prod_Estoque = remover_Quant_Produto(quantidade_Prod_Estoque, codigos_Barra, codigoBarra);
          codigos_Barra = remover_codigo_Produto(codigos_Barra, codigoBarra);

          System.out.println();
          System.out.println("DELETADO COM SUCESSO");

          //apaga as todas as infomações do estoque  
        } else if (opcao == 4) {

          valores_Produtos = apagarValores();
          quantidade_Prod_Estoque = apagarQuant();
          codigos_Barra = apagarProdutos();

          System.out.println();
          System.out.println("ESTOQUE DELETADO COM SUCESSO");
        }
        System.out.println();
        break;

        //mostra as informações contidas no estoque
      case 3:
        verificar_Estoque(codigos_Barra, quantidade_Prod_Estoque, valores_Produtos);
        System.out.println();
        break;
      case 4:
        //mostra as informações de um produto especifico
        verificar_Produto(codigos_Barra, valores_Produtos, quantidade_Prod_Estoque);
      }

    } while (opcao != 0);

  }

  public static int Menu() {
    Scanner input = new Scanner(System.in);
    System.out.println();
    System.out.println("GERENCIADOR DE ESTOQUE");
    System.out.println();
    System.out.println("[1] ADICIONAR PRODUTO");
    System.out.println("[2] ATUALIZAR ESTOQUE");
    System.out.println("[3] VERIFICAR ESTOQUE");
    System.out.println("[4] BUSCAR PRODUTO");
    System.out.println("[0] SAIR");
    System.out.println();
    System.out.print("Opcção: ");

    int opcao = input.nextInt();
    System.out.println();
    return opcao;
  }

  public static String[] adicionar_Codigo_Barra(String vetor[], String novoProduto) {
    String novoArray[] = new String[vetor.length + 1];

    for (int c = 0; c < vetor.length; c++) {
      novoArray[c] = vetor[c];
    }
    novoArray[vetor.length] = novoProduto;
    return novoArray;
  }

  public static double[] adicionar_Valor_Produto(double vetor[], double novoProduto) {
    double novoArray[] = new double[vetor.length + 1];

    for (int c = 0; c < vetor.length; c++) {
      novoArray[c] = vetor[c];
    }
    novoArray[vetor.length] = novoProduto;
    return novoArray;
  }

  public static int[] adicionar_Prod_Estoque(int vetor[], int novoProduto) {
    int novoArray[] = new int[vetor.length + 1];

    for (int c = 0; c < vetor.length; c++) {
      novoArray[c] = vetor[c];
    }
    novoArray[vetor.length] = novoProduto;
    return novoArray;
  }

  public static double[] alterar_Valor(double valorProdutos[], String arrayProdutos[]) {
    Scanner input = new Scanner(System.in);

    System.out.print("Insira nome do produto: ");
    String produto = input.nextLine();
    boolean mensagem = false;

    for (int c = 0; c < arrayProdutos.length; c++) {

      if (arrayProdutos[c].equalsIgnoreCase(produto)) {
        System.out.print("Insira novo valor: ");
        valorProdutos[c] = input.nextDouble();
        mensagem = true;
        break;
      }
    }
    if (mensagem) {
      System.out.println();
      System.out.print("ALTERADO COM SUCESSO");
    } else {
      System.out.println();
      System.out.print("PRODUTO NÃO ENCONTRADO");
    }
    return valorProdutos;
  }

  public static int[] alterar_Quantidade(String codigoBarra[], int arrayQtd[]) {
    Scanner input = new Scanner(System.in);

    System.out.print("Insira nome do produto: ");
    String produto = input.nextLine();
    boolean mensagem = false;

    for (int c = 0; c < codigoBarra.length; c++) {

      if (codigoBarra[c].equalsIgnoreCase(produto)) {
        System.out.print("Insira nova quantidade: ");
        arrayQtd[c] = input.nextInt();
        break;
      }
    }
    if (mensagem) {
      System.out.println();
      System.out.print("ALTERADO COM SUCESSO");
    } else {
      System.out.println();
      System.out.print("PRODUTO NÃO ENCONTRADO");
    }
    return arrayQtd;
  }

  public static void verificar_Estoque(String nomeProdutos[], int qtdProdutos[], double valorProdutos[]) {
  System.out.println("______________________________________________");
      System.out.println("[    PRODUTO   ]" + " " + "[QTD.PRODUTOS]" + " " + "[VALOR UNIDADE]");
      System.out.println("----------------------------------------------");
    for (int c = 0; c < nomeProdutos.length; c++) {
    
      System.out.println(
        "| " + nomeProdutos[c] + "  |     " + qtdProdutos[c] + "     |      " + valorProdutos[c] + "      |");
       System.out.println("----------------------------------------------");
    }
  }

  public static double[] remover_Valor_Produto(double valor[], String produtosEstoque[], String produto) {

    int contador = 0;
    for (int i = 0; i < produtosEstoque.length; i++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[i])) {
        contador++;
      }
    }
    double novoArray[] = new double[contador];
    contador = 0;

    for (int c = 0; c < valor.length; c++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[c])) {
        novoArray[contador++] = valor[c];
      }
    }
    return novoArray;
  }

  public static int[] remover_Quant_Produto(int qtd[], String produtosEstoque[], String produto) {

    int contador = 0;
    for (int i = 0; i < produtosEstoque.length; i++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[i])) {
        contador++;
      }
    }
    int novoArray[] = new int[contador];
    contador = 0;

    for (int c = 0; c < qtd.length; c++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[c])) {
        novoArray[contador++] = qtd[c];
      }
    }
    return novoArray;
  }

  public static String[] remover_codigo_Produto(String produtosEstoque[], String produto) {

    int contador = 0;
    for (int i = 0; i < produtosEstoque.length; i++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[i])) {
        contador++;
      }
    }
    String novoArray[] = new String[contador];
    contador = 0;

    for (int c = 0; c < produtosEstoque.length; c++) {
      if (!produto.equalsIgnoreCase(produtosEstoque[c])) {
        novoArray[contador++] = produtosEstoque[c];
      }
    }
    return novoArray;
  }

  public static void verificar_Produto(String arrayNomeProdutos[], double valorProd[], int arrayQtdProd[]) {
    Scanner input = new Scanner(System.in);

    System.out.print("Insira nome do Produto: ");
    String codigoBarra = input.nextLine();
    boolean msgNaoEncontrado = true;

    for (int c = 0; c < arrayNomeProdutos.length; c++) {
      if (arrayNomeProdutos[c].equalsIgnoreCase(codigoBarra)) {
        System.out.println();
        System.out.println("[PRODUTO]" + " " + "[QTD.PRODUTOS]" + " " + "[VALOR UNIDADE]");
        System.out.println("---------------------------------------------");
        System.out.println(
          "| " + arrayNomeProdutos[c] + "  |     " + arrayQtdProd[c] + "     |      " + valorProd[c] + "      |");
        msgNaoEncontrado = false;
        System.out.println();
        break;
      }
    }
    if (msgNaoEncontrado) {
      System.out.println();
      System.out.println("PRODUTO NÃO ENCONTRADO");
    }
  }

  public static String gerar_Codigo_Barra() {
    String impares = "13579";
    String pares = "02468";

    Random rdn = new Random();

    String novoCodigoBarra = "";
    for (int i = 0; i < 13; i++) {
      char numeros = ' ';
      if (i % 2 == 0) {
        numeros = impares.charAt(rdn.nextInt(0, 5));
      } else {
        numeros = pares.charAt(rdn.nextInt(0, 5));
      }
      novoCodigoBarra += numeros;
    }
    return novoCodigoBarra;
  }

  public static int[] apagarQuant() {
    int array[] = new int[0];
    return array;
  }

  public static double[] apagarValores() {
    double array[] = new double[0];
    return array;
  }

  public static String[] apagarProdutos() {
    String array[] = new String[0];
    return array;
  }
}