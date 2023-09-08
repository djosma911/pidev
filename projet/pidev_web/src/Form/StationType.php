<?php

namespace App\Form;

use App\Entity\MoyenTransport;
use App\Entity\Station;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CollectionType;
use Symfony\Component\Form\FormBuilderInterface;
use App\Form\CommuneType;
use App\Entity\Ligne; // Import the Ligne entity

use Symfony\Component\OptionsResolver\OptionsResolver;

class StationType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('long_alt')
            ->add('ligne', EntityType::class, [ 
                'class' => Ligne::class, 
                'choice_label' => 'nom_ligne', 
            ])
            ;
    } 

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Station::class,
        ]);
    }
}
