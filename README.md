# Library Manager
Tabalho apresentado a matéria de Desenvolvimento Backend para o curso de pós graduação em Desenvolvimento de Aplicativos Móveis da PUCPR

Professor: Vinicius Godoy

Entrega: 09/07/2023

## Integrantes do grupo
- Bruno Thuma
- Matheus Mei
- Raison Robert

## Objetivo
Sistema de gestão de emprestimos de livros de uma biblioteca.

Livros cadastrados podem ser emprestados por usuarios logados.
## Regras dos casos de uso
- Ações de put e post na classe usuário exigem autenticação
- Apenas usuarios `role==ADMIN` podem deletar usuarios
- Para um usuario ser deletado, deve existir pelo menos um usuarios `role==ADMIN` no sistema apos a operação
- Usuarios logados podem adicionar livros a suas listas, caracterizando emprestimo
 
## Link para apresentação

https://youtu.be/VLXcsPlwSVs