import { Aeronave } from "types/aeronave";
import { useForm, Controller } from 'react-hook-form';
import { useEffect, useState } from "react";
import Select from 'react-select';
import { AxiosParams } from "types/vendor/axios";
import { BASE_URL } from "util/request";
import axios from "axios";
import { toast } from 'react-toastify';
import './styles.css';
import { Marcas } from "types/marcas";
import { SelectOptionsCombo } from "types/selectVendidos";
import { AeronaveInsert } from "types/aeronaveInsert";

type Props = {
    onSubmitChange: Function;
};

const Form = ({ onSubmitChange }: Props) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
        setValue,
        control,
    } = useForm<AeronaveInsert>();


    const optionsMarca = [
        { valor: false, label: 'Airbus' },
        { valor: true, label: 'Boeing' },
        { valor: true, label: 'Embraer' }
    ]

    const optionsVendido = [
        { valor: false, label: 'Vendido' },
        { valor: true, label: 'Não vendido' }
    ]



    const onSubmit = (formData: AeronaveInsert) => {

        const aeronave: Aeronave = {
            id: 0,
            nome: formData.nome,
            marca: formData.marca.label,
            ano: formData.ano,
            descricao: formData.descricao,
            vendido: formData.vendido.valor
        };

        const data = {
            ...aeronave
        };

        console.log(data);

        const params: AxiosParams = {
            method: 'POST',
            url: `${BASE_URL}/aeronaves`,
            data
        }

        console.log(data)
        axios(params).then(response => {
            setValue('nome', '');
            setValue('descricao', '')
            setValue('ano', 0);


            onSubmitChange();
            toast.info("Aeronave salva com sucesso");
        }).catch((error) => {
            console.log(error.response.data.errors[0]);
            toast.error(error.response.data.errors[0].fieldName + ':  ' + error.response.data.errors[0].message);
        });


    }


    return (
        <div className="base-card">
            <h1>Gestão de Aeronaves</h1>

            <div className="form-container">

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div>
                        <div className="col-lg-6">
                            <div className="row">
                                <div className="col-sm-4">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('nome', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.nome ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Modelo"
                                            name="nome"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.nome?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-4">
                                    <div className="margin-bottom-30 ">
                                        <Controller
                                            name="marca"
                                            rules={{ required: true }}
                                            control={control}
                                            render={({ field }) => (
                                                <Select
                                                    {...field}
                                                    options={optionsMarca}
                                                    placeholder="Marca"
                                                    getOptionLabel={(test: SelectOptionsCombo) => String(test.label)}
                                                    getOptionValue={(test: SelectOptionsCombo) => String(test.valor)}
                                                />
                                            )}
                                        />
                                        {errors.marca && (
                                            <div className="invalid-feedback d-block">
                                                Campo obrigatório
                                            </div>
                                        )}
                                    </div>
                                </div>

                                <div className="col-sm-4">
                                    <div className="margin-bottom-30 ">
                                        <Controller
                                            name="vendido"
                                            rules={{ required: true }}
                                            control={control}
                                            render={({ field }) => (
                                                <Select
                                                    {...field}
                                                    options={optionsVendido}
                                                    getOptionLabel={(test: SelectOptionsCombo) => String(test.label)}
                                                    getOptionValue={(test: SelectOptionsCombo) => String(test.valor)}
                                                />
                                            )}
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.vendido?.message}
                                        </div>
                                    </div>
                                </div>

                            </div>



                            <div className="row">
                                <div className="col-sm-6">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('descricao', {

                                            })}
                                            type="text"
                                            className={`form-control base-input ${errors.descricao ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Descrição"
                                            name="descricao"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.descricao?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-3">
                                    <div className="margin-bottom-30">
                                        <input
                                            {...register('ano', {
                                                required: 'Campo obrigatório',
                                            })}
                                            type="number"
                                            className={`form-control base-input ${errors.ano ? 'is-invalid' : ''
                                                }`}
                                            placeholder="Ano"
                                            name="ano"
                                        />
                                        <div className="invalid-feedback d-block">
                                            {errors.descricao?.message}
                                        </div>
                                    </div>
                                </div>

                                <div className="col-sm-3">
                                    <button className="btn btn-primary product-crud-button text-white">
                                        SALVAR
                                    </button>

                                </div>

                            </div>


                        </div>

                    </div>

                </form>

            </div>

        </div>
    );
}

export default Form;