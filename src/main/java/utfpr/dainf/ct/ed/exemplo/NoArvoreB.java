package utfpr.dainf.ct.ed.exemplo;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Representa uma página (nó) de uma árvore B.
 * @param <K> O tipo de dado da chave.
 * @author Wilson Horstmeyer Bogado <wilson@utfpr.edu.br>
 */
public class NoArvoreB<K extends Comparable<? super K>> {
    protected int numChaves;
    protected boolean folha;
    protected K[] chaves;
    protected NoArvoreB<K>[] filhos;

    /**
     * Cria um nó vazio.
     */
    public NoArvoreB() {
    }

    /**
     * Retorna o número atual de chaves armazenadas no nó.
     * @return O número de chaves armazenadas no nó
     */
    public int getNumChaves() {
        return numChaves;
    }

    /**
     * Inicializa o número de chaves armazenadas no nó.
     * Deve ser usado quando novas chaves são insidas.
     * @param numChaves O número de chaves armazenadas no nó.
     */
    public void setNumChaves(int numChaves) {
        this.numChaves = numChaves;
    }

    /**
     * Retorna {@code true} se o nó for uma folha.
     * @return {@code true} se o nó for uma folha
     */
    public boolean isFolha() {
        return folha;
    }

    /**
     * Inicializa o atributo de folha de uma nó.
     * {@code true} indica que o nó é uma folha.
     * @param folha O atributo de folha do nó
     */
    public void setFolha(boolean folha) {
        this.folha = folha;
    }

    /**
     * Retorn o vetor de chaves do nó.
     * Somente as primeiras {@code numChaves} são válidas.
     * @return O vetor de chaves do nó
     */
    public K[] getChaves() {
        return chaves;
    }

    /**
     * Substituí a chave na posição {@code i} pelo valor especificado.
     * @param chave O valor da chave
     * @param i A posição da chave
     */
    public void setChave(K chave, int i) {
        chaves[i] = chave;
    }
    
    /**
     * Retorna a chave na posição especificada.
     * @param i A posição da chave
     * @return A chave na posição especificada
     */
    public K getChave(int i) {
        return chaves[i];
    }
    
    /**
     * Inicializa o vetor de chaves do nó.
     * @param chaves O vetor de chaves
     */
    public void setChaves(K[] chaves) {
        this.chaves = chaves;
    }

    /**
     * Retorna o vetor de referências para os filhos do nó.
     * Somente as primeiras {@code numChaves+1} são válidas 
     * @return O vetor de filhos do nó
     */
    public NoArvoreB<K>[] getFilhos() {
        return filhos;
    }

    /**
     * Inicializa o vetor de filhos do nó.
     * @param filhos O vetor de filhos do nó
     */
    public void setFilhos(NoArvoreB<K>[] filhos) {
        this.filhos = filhos;
    }
    
    /**
     * Substitui o filho do nó na posição especificada.
     * @param filho O filho a ser armazenado
     * @param i A posição do filho
     */
    public void setFilho(NoArvoreB<K> filho, int i) {
        filhos[i] = filho;
    }
    
    /**
     * Retorna o filho ndo nó na posição especificada.
     * @param i A posição do filho
     * @return O filho do nó
     */
    public NoArvoreB<K> getFilho(int i) {
        return filhos[i];
    }
    
    /**
     * Retorna {@code true} se o nó especificado for igual a este nó.
     * As igualdade dos valores das chaves, tamanho e folha é verificada, ou seja
     * esta é uma comparação rasa. Comparar os filhos equivaleria a comparar toda
     * a subárvore.
     * @param outro O nó a ser comparado
     * @return {@code true} se os nós forem iguais
     */
    @Override
    public boolean equals(Object outro) {
        boolean equal = false;
        if (outro instanceof NoArvoreB) {
            NoArvoreB<K> no = (NoArvoreB) outro;
            equal = numChaves == no.numChaves && folha == no.folha;
            equal = equal && chaves != null && Arrays.equals(chaves, no.chaves);
        }
        return equal;
    }

    /**
     * Retorna um hash para o nó.
     * @return O hash do nó.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.numChaves;
        hash = 59 * hash + (this.folha ? 1 : 0);
        hash = 59 * hash + Arrays.deepHashCode(this.chaves);
        hash = 59 * hash + Arrays.deepHashCode(this.filhos);
        return hash;
    }

    /**
     * Retorna uma representação textual do nó.
     * O formato retornado é [k0 k1 ...]
     * @return A representação textual do nó
     */
    @Override
    public String toString() {
        return Arrays.stream(chaves)
                .limit(numChaves)
                .map(c -> c.toString())
                .collect(Collectors.joining(" ", "[", "]"));
    }
}
