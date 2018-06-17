
import utfpr.dainf.ct.ed.exemplo.ArvoreB;

/**
 * UTFPR - Universidade Tecnológica Federal do Paraná
 * DAINF - Departamento Acadêmico de Informática
 * 
 * Teste de implementação de árvore B.
 * 
 * @author Wilson Horstmeyer Bogado <wilson@utfpr.edu.br>
 */
public class Main {
    
    public static void main(String[] args) {
        Main main = new Main();
        main.testaArvoreB();
    }
    
    private void testaArvoreB() {
        System.out.println("TESTE DE ARVORE B");
        
        ArvoreB<Integer> a = new ArvoreB<>(3);
        a.insere(30);
        a.insere(70);
        a.insere(50);
        a.insere(10);
        a.insere(90);
        a.insere(40);
        a.insere(95);
        a.insere(20);
        a.insere(60);
        a.insere(45);
        a.insere(80);
        a.insere(25);
        a.insere(75);
        System.out.println("Árvore B: " + a);
    }
}
