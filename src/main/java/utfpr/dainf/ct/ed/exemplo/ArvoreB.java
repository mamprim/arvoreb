package utfpr.dainf.ct.ed.exemplo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * UTFPR - Universidade Tecnológica Federal do Paraná
 * DAINF - Departamento Acadêmico de Informática
 * 
 * Exemplo de implementação de árvore AVL.
 * @author Wilson Horstmeyer Bogado <wilson@utfpr.edu.br>
 * @param <K> O tipo de dado da chave.
 */
public class ArvoreB<K extends Comparable<? super K>> {
    protected int grauMin;
    protected NoArvoreB<K> raiz;

    /**
     * Cria uma árvore AVL com grau mínimo especificado.
     * Lança uma exceção caso ao grau especificado seja menor do que 2.
     * @param grauMin O grau mínimo
     * @throws RuntimeException Caso t < 2
     */
    public ArvoreB(int grauMin) {
        if (grauMin < 2) {
            throw new RuntimeException("Grau mínimo deve ser >= 2");
        }
        this.grauMin = grauMin;
        raiz = alocaNo();
    }

    /**
     * Retorna o grau mínimo da árvore.
     * @return O grau mínimo da árvore
     */
    public int getGrauMin() {
        return grauMin;
    }

    /**
     * Retorna a raiz da árvore.
     * @return A raiz da árvore
     */
    public NoArvoreB<K> getRaiz() {
        return raiz;
    }
    
    /**
     * Cria e inicializa um novo nó.
     * @return O novo nó criado
     */
    protected NoArvoreB<K> alocaNo() {
        NoArvoreB<K> no = new NoArvoreB<>();
        no.setChaves((K[])new Comparable[2*grauMin-1]);
        no.setFilhos(new NoArvoreB[2*grauMin]);
        no.setFolha(true);
        no.setNumChaves(0);
        return no;
    }
    
    /**
     * Classe auxiliar para retornar o resultado de busca de uma chave.
     */
    public class ResultadoBusca {
        private final NoArvoreB<K> no;
        private final int indiceChave;

        public ResultadoBusca(NoArvoreB<K> no, int indiceChave) {
            this.no = no;
            this.indiceChave = indiceChave;
        }

        public NoArvoreB<K> getNo() {
            return no;
        }

        public int getIndiceChave() {
            return indiceChave;
        }

        @Override
        public String toString() {
            return String.format("(%s, %d)", no, indiceChave);
        }

        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof ArvoreB.ResultadoBusca) {
                ResultadoBusca rb = (ResultadoBusca) obj;
                equal = no.equals(rb.no) && indiceChave == rb.indiceChave; 
            }
            return equal;
        }
        
    }
    
    /**
     * Busca o nó com a chave especificada.
     * @param x A subárvore a ser pesquisa
     * @param k A chave procurada
     * @return O resultado da busca ou {@code null}
     */
    protected ResultadoBusca busca(NoArvoreB<K> x, K k) {
        if (x == null){
            return null;
        }
        if (k.equals(130)){
            int index = 0;
        }
        for(int i = 0; i < x.getNumChaves(); i++){
            if (x.getChave(i).equals(k)){
                return new ResultadoBusca(x, i);
            } else if (k.compareTo(x.getChave(i)) < 0){
                return busca(x.getFilho(i), k);
            }
            if (i == x.getNumChaves()-1){
                return busca(x.getFilho(i+1), k);
            }
        }
        //return busca(x.getFilho(2*grauMin),k);
        return null;
    }
    
    /**
     * Busca o nó com a chave especificada.
     * @param k A chave procurada
     * @return O resultado da busca ou {@code null}
     */
    public ResultadoBusca busca(K k) {
        return busca(raiz, k);
    }
    
    /**
     * Divide um filho cheio de modo a abrir espaço para inserção.
     * O nó passado no primeiro argumento não pode estar cheio.
     * @param x Um nó não cheio
     * @param i O índice do filho cheio
     * @param y O filho cheio
     */
    protected void divideFilho(NoArvoreB<K> x, int i, NoArvoreB<K> y) {
        NoArvoreB<K> esquerda = alocaNo();
        K chaves_esq[] = (K[])new Comparable[2*grauMin - 1];
        K chaves_dir[] = (K[])new Comparable[2*grauMin - 1];
        K chave_meio = y.getChave((2*grauMin-1)/2);
        for(int j = 0; j < y.getChaves().length; j++){
            if (j < (2*grauMin-1)/2){
                chaves_esq[j] = y.getChave(j);
                esquerda.setFilho(y.getFilho(j), j);
                if (j == (2*grauMin-1)/2 - 1){
                    esquerda.setFilho(y.getFilho(j+1), j+1);
                }
            }
            if (j > (2*grauMin-1)/2){
                chaves_dir[j - ((2*grauMin-1)/2 + 1)] = y.getChave(j);
                y.setFilho(y.getFilho(j), j - ((2*grauMin-1)/2+1));
                y.setFilho(null,j);
                if (j == y.getChaves().length - 1 && 
                    y.getFilhos().length > y.getChaves().length){
                    y.setFilho(y.getFilho(y.getChaves().length), j + 1 - ((2*grauMin-1)/2+1));
                    y.setFilho(null,y.getChaves().length);
                }
            }
        }
        if (esquerda.getFilho(0) != null){
            esquerda.setFolha(false);
        }
        esquerda.setChaves(chaves_esq);
        esquerda.setNumChaves(grauMin-1);
        if (y.getFilho(0) != null){
            y.setFolha(false);
        }
        y.setChaves(chaves_dir);
        y.setNumChaves(grauMin);
        
        NoArvoreB<K> aux = null;
        NoArvoreB<K> aux2 = x.getFilho(i);
        for(int j = i; j < grauMin*2; j++){
            x.setFilho(aux, j);
            aux = aux2;
            if (j < grauMin*2-1){
                aux2 = x.getFilho(j+1);
            }
        }
        insereNaoCheio(x, chave_meio);
        x.setFolha(false);
        x.setFilho(esquerda, i);
        x.setFilho(y, i+1);
    }
    
    /**
     * Insere uma nova chave em um nó não cheio.
     * O nó passado no primeiro argumento não pode estar cheio.
     * @param x Um nó não cheio
     * @param k A chave a ser inserida no nó
     */
    protected void insereNaoCheio(NoArvoreB<K> x, K k) {
        x.setNumChaves(x.getNumChaves() + 1);
        for (int i =0; i < x.getChaves().length; i++){
            if (x.getChave(i) == null){
                x.setChave(k, i);
                break;
            }
            if (k.compareTo(x.getChave(i)) == -1){
                K aux;
                K aux2 = k;
                for (int j = i; j < x.getChaves().length; j++){
                    aux = x.getChave(j);
                    x.setChave(aux2, j);
                    aux2 = aux;
                }
                break;
            }
        }
        //x.setChave(k, );
    }
    public boolean inserePai(NoArvoreB<K> pai, NoArvoreB<K> no, int index){
        if (pai.getNumChaves() >= 2*grauMin - 1){
            K total[] = (K[])new Comparable[2*grauMin];
            NoArvoreB<K> total_filhos[] = new NoArvoreB[2*grauMin + 1];
            for(int i = 0; i < pai.getChaves().length; i++){
                total[i] = pai.getChave(i);
            }
            for(int i = 0; i < pai.getFilhos().length; i++){
                total_filhos[i] = pai.getFilho(i);
            }
            total[index] = no.getChave(0);
            int numChaves = index;
            for(int i = index + 1; i < total.length; i++){
                total[i] = pai.getChave(i-1);
                if (total[i] != null){
                    numChaves++;
                }
            }
            total_filhos[index] = no.getFilho(0);
            total_filhos[index+1] = no.getFilho(1);
            for (int i = index + 2; i < total_filhos.length; i++){
                total_filhos[i] = pai.getFilho(i-1);
            }
            pai.setChaves(total);
            pai.setNumChaves(numChaves);
            pai.setFilhos(total_filhos);
            return true;
        }
        K total[] = (K[])new Comparable[2*grauMin - 1];
        NoArvoreB<K> total_filhos[] = new NoArvoreB[2*grauMin];
        for(int i = 0; i < pai.getChaves().length; i++){
            total[i] = pai.getChave(i);
        }
        for(int i = 0; i < pai.getFilhos().length; i++){
            total_filhos[i] = pai.getFilho(i);
        }
        total[index] = no.getChave(0);
        int numChaves = index;
        for(int i = index + 1; i < total.length; i++){
            total[i] = pai.getChave(i-1);
            if (total[i] != null){
                numChaves++;
            }
        }
        total_filhos[index] = no.getFilho(0);
        total_filhos[index+1] = no.getFilho(1);
        for (int i = index + 2; i < total_filhos.length; i++){
            total_filhos[i] = pai.getFilho(i-1);
        }
        pai.setChaves(total);
        pai.setNumChaves(numChaves);
        pai.setFilhos(total_filhos);
        return false;
    }
    public void insereCheio(List<NoArvoreB<K>> list, NoArvoreB<K> no_cheio){
        NoArvoreB<K> pai = list.remove(list.size()-1);
        NoArvoreB<K> aux_no = alocaNo();
        int index = 0;
        for (int i = 0; i < pai.getFilhos().length; i++){
            if (pai.getFilho(i) == no_cheio){
                index = i;
                break;
            }
        }
        divideFilho(aux_no, 0, no_cheio);
        if (inserePai(pai, aux_no, index)){
            if (pai == raiz){
                NoArvoreB<K> nova_raiz = alocaNo();
                divideFilho(nova_raiz, 0, pai);
                raiz = nova_raiz;
            } else {
                insereCheio(list, pai);
            }
        }
        
    }
    /**
     * Insere a chave especificada n árvore.
     * @param k A chave a ser inserida
     */
    public void insere(K k) {
        NoArvoreB<K> aux = raiz;
        NoArvoreB<K> pai = null;
        List<NoArvoreB<K>> pai_list = new ArrayList();
        boolean inserted = false;
        int index = 0;
        do {
            if (aux.isFolha()){
                if (aux.getNumChaves() < grauMin*2 - 1){
                    insereNaoCheio(aux, k);
                } else {
                    /*Split*/
                    K total[] = (K[])new Comparable[2*grauMin];
                    for (int i = 0; i < total.length; i++){
                        if (i != total.length - 1){
                            if (aux.getChave(i).compareTo(k) >= 0){
                                total[i] = k;
                                for (int j = i; j < aux.getChaves().length; j++){
                                    total[j+1] = aux.getChave(j);
                                }
                                break;
                            } else {
                                total[i] = aux.getChave(i);
                            }
                        } else {
                            total[i] = k;
                        }
                    }
                    aux.setChaves(total);
                    if (pai == null){
                        pai = alocaNo();
                        pai.setFolha(false);
                        divideFilho(pai, 0, aux);
                        raiz = pai;
                        break;
                    } else if (pai.getNumChaves() < 2*grauMin - 1){
                        for (int i = 0; i < pai.getFilhos().length; i++){
                            if (pai.getFilho(i) == aux){
                                index = i;
                                break;
                            }
                        }
                        divideFilho(pai, index, aux);
                    } else {
                        insereCheio(pai_list, aux);
                        //throw new RuntimeException("Implementar, para quando no pai está cheio");
                    }
                    //throw new RuntimeException("AInda n");
                }
                inserted = true;
            } else {
                for (int i = 0; i < aux.getNumChaves(); i++){
                    if (aux.getChave(i).compareTo(k) >= 0){
                        pai_list.add(aux);
                        pai = aux;
                        aux = aux.getFilho(i);
                        break;
                    }
                    if (i == aux.getNumChaves()-1){
                        pai_list.add(aux);
                        pai = aux;
                        aux = aux.getFilho(aux.getNumChaves());
                        break;
                    }
                }
            }
        } while (!inserted);
        calculaNumChaves(raiz);
    }
    public void calculaNumChaves(NoArvoreB<K> no){
        if (no != null){
            int index = 0;
            for (int i = 0; i < no.getChaves().length; i++){
                if (no.getChave(i) == null){
                    index = i;
                    break;
                }
                index = i+1;
            }
            no.setNumChaves(index);
            for (int i = 0; i < no.getFilhos().length; i++){
                calculaNumChaves(no.getFilho(i));
            }
        }
    }
    
    /**
     * Grava o nó especificado no armazenamento secundário.
     * Nesta implementação, toda a árvore permanece na memória, por isso
     * este método não necessita nenhuma ação.
     * Para implementações que usem outro tipo de armazenamento secundário,
     * deve ser sobrecarregado de acordo.
     * @param no O nó a ser gravado
     */
    protected void escritaNo(NoArvoreB<K> no) {
    }

    /**
     * Carrega o nó especificado do armazenamento secundário.
     * Nesta implementação, toda a árvore permanece na memória, por isso
     * este método não necessita nenhuma ação.
     * Para implementações que usem outro tipo de armazenamento secundário,
     * deve ser sobrecarregado de acordo.
     * @param no O nó a ser carregado 
     */
    protected void leituraNo(NoArvoreB<K> no) {
    }
    
    /**
     * Compara duas árvores B.
     * Os nós são percorridos recursivamente e comparados.
     * @param outro A árvore a ser compaprada
     * @return 
     */
    @Override
    public boolean equals(Object outro) {
        boolean equal;
        equal = outro instanceof ArvoreB;
        if (equal) {
            ArvoreB<K> a = (ArvoreB<K>) outro;
            NoArvoreB<K> r1 = getRaiz();
            NoArvoreB<K> r2 = a.getRaiz();
            equal = r1.equals(r2); 
            for (int i = 0; equal && i < r1.getNumChaves(); i++) {
                if (!r1.isFolha()) {
                    equal = r1.getFilho(i).equals(r2.getFilho(i));
                }
            }
        }
        return equal;
    }

    /**
     * Retorna um hash para a árvore.
     * @return O hash para a árvore.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.grauMin;
        hash = 29 * hash + Objects.hashCode(this.raiz);
        return hash;
    }

    /**
     * Retorna uma representação textual da árvore.
     * Percorre toda a árvore recursivamente.
     * O formato da representação é
     * [k0 k1 ...], ([m0 m1 ...]), [n0 n1 ...])
     * @param raiz A raiz da subárvore
     * @return Uma representação textual da árvore
     */
    protected String toString(NoArvoreB<K> raiz) {
        StringBuilder sb = new StringBuilder();
        if (raiz.isFolha()) {
            sb.append(raiz);
        } else {
            String s = Arrays.stream(raiz.getFilhos())
                    .limit(raiz.getNumChaves()+1)
                    .map(f -> toString(f))
                    .collect(Collectors.joining(",", "(" + raiz + ", (", "))"));
            sb.append(s);
        }
        return sb.toString();
    }
    
    /**
     * Retorna uma representação textual da árvore.
     * O formato da representação é
     * ([k0 k1 ...], ([m0 m1 ...]), [n0 n1 ...]))
     * @return Uma representação textual da árvore.
     */
    @Override
    public String toString() {
        return toString(raiz);
    }
}
