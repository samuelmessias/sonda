import { SelectOptionsCombo } from './selectVendidos';

export type AeronaveInsert = {
  id: number;
  nome: string;
  marca: SelectOptionsCombo;
  ano: number;
  descricao: string;
  vendido: SelectOptionsCombo;
};
