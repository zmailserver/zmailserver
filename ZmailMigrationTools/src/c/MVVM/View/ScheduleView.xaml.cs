﻿using MVVM.ViewModel;
using System.Windows;

namespace MVVM.View
{
public partial class ScheduleView
{
    public ScheduleView()
    {
        InitializeComponent();
        string sdp = (string)Application.Current.Properties["sdp"];
        if (sdp.StartsWith("d"))
        {
            datePickerSched.Visibility = System.Windows.Visibility.Hidden;
            Datebox2.Visibility = System.Windows.Visibility.Visible;
            DateboxLbl2.Visibility = System.Windows.Visibility.Visible;
        }
    }

    // Kind of a drag that we have to put these next 2 methods in here, but PasswordBox is not a dependency property,
    // so we can't bind to the model.  Doing this for now -- should probably use an attached property later
    private ScheduleViewModel ViewModel {
        get { return DataContext as ScheduleViewModel; }
    }
    private void pb_DefPasswordChanged(object sender, RoutedEventArgs e)
    {
        ViewModel.DefaultPWD = ipwBox.Password;
    }
}
}
